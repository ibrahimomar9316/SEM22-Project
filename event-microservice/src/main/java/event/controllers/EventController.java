package event.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import event.authentication.AuthManager;
import event.datatransferobjects.EventIdsDto;
import event.datatransferobjects.RuleDto;
import event.domain.entities.Event;
import event.domain.enums.Rule;
import event.domain.objects.Participant;
import event.foreigndomain.entitites.Message;
import event.models.EventCreationModel;
import event.models.EventJoinModel;
import event.models.EventRulesModel;
import event.service.EventService;
import event.service.MessageService;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * This is the controller for the Event api.
 * It manages endpoints for creating, joining and
 * getting events.
 *
 * <p>It is called by an authenticated user by inputting
 * JSON messages in the body and the token received
 * from /authenticate in Authentication Microservice.</p>
 */
@RestController
@RequestMapping({"/api"})
@SuppressWarnings("PMD")
public class EventController {

    @Autowired
    private RestTemplate restTemplate;

    // This is used to set, get and check for events in
    // the H2 repo
    private transient EventService eventService;

    private transient MessageService messageService;

    // This is used and initiated to check if the token is
    // valid and to get the netID
    private final transient AuthManager auth;

    private static final int OK = 200;


    /**
     * Instantiates a new EventController.
     *
     * @param eventService   the event service
     *                       This class controls the interaction
     *                       between the Event repository and the
     *                       JSON interface.
     * @param messageService the messaging service.
     *                       This class controls the messages that get sent
     *                       when a user applies for a position or
     *                       leaves an event
     * @param auth           the auth manager
     *                       This verifies the token and also gets
     *                       the netID of the verified user from their
     *                       token
     */
    @Autowired
    public EventController(EventService eventService, MessageService messageService, AuthManager auth) {
        this.eventService = eventService;
        this.messageService = messageService;
        this.auth = auth;
    }

    /**
     * Gets rest template.
     *
     * @return the rest template
     */
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    /**
     * Endpoint for creating a new event in the database.
     *
     * <p>This is used to create an event by inputting an EventCreationModel
     * that takes the JSON and specifies the event type (Competition or
     * Training).</p>
     *
     * <p>It creates an event with the given attributes and it attributes the admin
     * of the event to the netID in the token.</p>
     *
     * <p>It returns a message conveying the new event, with HTTP.ok response.</p>
     *
     * <p>If an authorization header is not provided or is invalid, it returns
     * 401 Unauthorized, implemented in JWTRequestFilter.</p>
     *
     * @param request the creation model used to transmit the event type
     * @return HTTP.ok and a message if authorization header correct
     */
    @PostMapping({"/event/create"})
    public ResponseEntity<String> create(
            @RequestBody EventCreationModel request) {
        // Creates new event from model event type and attributes it to the
        // netID in the token.
        if (request.getEventType() == null || request.getTime() == null
                || request.getParticipants() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid JSON or event type!");
        }
        Event savedEvent = new Event(request.getEventType(),
                auth.getNetId(),
                request.getTime(),
                request.getParticipants());

        // Saves event to database using eventService
        eventService.saveEvent(savedEvent);
        // returns OK and a string implemented in Event
        return ResponseEntity.ok(savedEvent.toString());
    }

    /**
     * Endpoint for getting all the events in the database.
     *
     * <p>This does not implement any filtering of events</p>
     *
     * @return The Events as a list of strings
     */
    @GetMapping({"/event/getAll"})
    public ResponseEntity<String> getAll() {
        // checks if the database is not empty
        if (eventService.getAllEvents().size() != 0) {
            return ResponseEntity.ok(eventService.getAllEvents().toString());
        } else {
            // else returns BAD_REQUEST
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("There are no events for you to join!");
        }
    }

    /**
     * Endpoint for joining an event in the database.
     * <p>It takes the id of the event and searches
     * for it in the database. When it finds the
     * corresponding event, it adds the user to
     * the participants if and only if the user
     * is not already in the list.</p>
     *
     * <p>It returns a string message reporting
     * if the user has successfully joined the event.
     * If some error occurs, it returns 400 and a
     * message conveying the error.</p>
     *
     * @param request a model containing the id of the event as a long
     * @return one of four messages and either 200, 400 or 500
     */
    @PostMapping({"/event/join"})
    public ResponseEntity<String> join(@RequestHeader("Authorization") String token, @RequestBody EventJoinModel request) {
        try {
            Event event = eventService.getEvent(request.getEventId());
            if (event == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Event not found!");
            }
            String netId = auth.getNetId();
            if (event.getParticipants()
                    .stream()
                    .map(Participant::getNetId)
                    .collect(Collectors.toList())
                    .contains(netId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("You are already participating in this event!");
            }

            List<Participant> participants = event.getParticipants()
                    .stream()
                    .filter(x -> x.getPosition() == request.getPosition()
                            && x.getNetId() == null)
                    .collect(Collectors.toList());
            if (participants.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("This position is already filled");
            }

            HttpStatus status = messageService.sendJoinMessage(token, request, netId, event.getAdmin());
            if (status.value() != OK) {
                return new ResponseEntity<>(status);
            }

            return ResponseEntity.ok("You have sent a request to join event: " + event.getEventId()
                    + " made by " + event.getAdmin());
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ConnectException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Message service could not be reached");
        }
    }

    /**
     * API endpoint used for adding users to events only by the admin after
     * they accepted a join-request.
     *
     * @param request The message holding the join request
     * @return 200 if the user is successfully added to the even
     *         400 if the user is already participating or there is no place left
     *         404 if the event is not found
     */
    @PostMapping({"/event/add"})
    public ResponseEntity<String> joinByAdmin(@RequestBody Message request) {
        try {
            Event event = eventService.getEvent(request.getEventId());
            if (event.getParticipants()
                    .stream()
                    .map(Participant::getNetId)
                    .collect(Collectors.toList())
                    .contains(request.getSender())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User is already participating in this event");
            }

            List<Participant> participants = event.getParticipants()
                    .stream()
                    .filter(x -> x.getPosition() == request.getPosition() && x.getNetId() == null)
                    .collect(Collectors.toList());
            if (participants.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("This position is already filled");
            }

            participants.get(0).setNetId(request.getSender());
            eventService.updateEvent(event);
            return ResponseEntity.ok("You have added " + request.getSender() + " to your event");
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint for leaving an event.
     * <p>It takes the id of the event and searches
     * for it in the database. When it finds the
     * corresponding event, if the user is already
     * in the list, it removes them from the database</p>
     *
     * <p>It returns a string message reporting
     * if the user has successfully removed from the event.</p>
     *
     * @param request a model containing the id of the event as a long
     * @return 200 OK if successfull.
     *         400 BAD_REQUEST if the user is not in the specified event
     *         404 NOT_FOUND if the event id ws not found in the database
     *         503 SERVICE_UNAVAILABLE if the messageService could not be reached
     */
    @PostMapping({"/event/leave"})
    public ResponseEntity<String> leave(@RequestHeader("Authorization") String token, @RequestBody EventJoinModel request) {
        try {
            Event event = eventService.getEvent(request.getEventId());
            List<Participant> participant = event.getParticipants()
                    .stream()
                    .filter(x -> auth.getNetId().equals(x.getNetId()))
                    .collect(Collectors.toList());
            if (participant.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("You are not participating in this event!");
            }

            HttpStatus status = messageService.sendLeaveMessage(token, request, auth.getNetId(), event.getAdmin());
            if (status.value() != OK) {
                return new ResponseEntity<>(status);
            }

            participant.get(0).setNetId(null);
            eventService.updateEvent(event);
            return ResponseEntity.ok("You have left event " + event.getEventId()
                    + " made by " + event.getAdmin());
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ConnectException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Message service could not be reached");
        }
    }

    /**
     * This endpoint is used my an admin of the event to see
     * the list of events they created.
     * TODO: show notifications for each event.
     *
     * @return the list of events the admin created
     */
    @GetMapping({"/event/myCreatedEvents"})
    public ResponseEntity<String> myCreatedEvents() {
        return ResponseEntity.ok(eventService.getEventsByAdmin(auth.getNetId()).toString());
    }

    /**
     * This endpoint is used my a rower to see the list
     * of events they joined.
     * TODO: show notifications for each event.
     *
     * @return the list of events the user has joined
     */
    @GetMapping({"/event/myJoinedEvents"})
    public ResponseEntity<String> myJoinedEvents() {
        return ResponseEntity.ok(eventService.getEventsByParticipant(auth.getNetId()).toString());
    }

    /**
     * Endpoint for updating events. Only admins of their own events are able to update their events
     * no one else can. All fields except the id can be updated / changed.
     *
     * @param event The new updated event that has to be stored in the database
     * @return A responseEntity with a 200 OK message if the event was indeed updated
     *         A responseEntity with a 401 UNAUTHORIZED message if the user who sent request
     *         is not the admin of the un-updated event
     *         A responseEntity with a 404 NOT_FOUND message if the event was not found
     */
    @PostMapping({"/event/update"})
    public ResponseEntity<String> update(@RequestBody Event event) {
        try {
            Event old = eventService.getEvent(event.getEventId());
            if (!old.getAdmin().equals(auth.getNetId())) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            eventService.updateEvent(event);
            return ResponseEntity.ok(event.toStringUpdate());
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint for setting the rules of the event. Only admins of their own events
     * are able to set their rules, no one else can. If the authorised user is the admin,
     * then they can specify some rules of the event, like if they want the event to only be
     * for professional rowers. After specifying these rules, they get sent to the Certificate
     * Microservice, where an index is calculated and sent back to event/update so that it is
     * store in EventRepo.
     *
     * @param token the token containing the netID of the admin
     * @param rules an EventRulesModel that contains rules about gender selection,
     *              professional players only and about what certificate te cox needs.
     * @return a string response stating if the event has been correctly updated to contain
     *         the rule index.
     * @throws NotFoundException    this is thrown if the event specified cannot be found
     */
    @PostMapping({"/event/setRules"})
    public ResponseEntity<String> setRules(@RequestHeader("Authorization") String token,
                                           @RequestBody EventRulesModel rules)
                                            throws NotFoundException, JsonProcessingException {
        Event event = eventService.getEvent(rules.getEventId());
        if (!event.getAdmin().equals(auth.getNetId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("You are not the admin of this event!");
        }
        RuleDto dto = new RuleDto(rules.getEventId(), rules.getGenderConstraint(),
                rules.isProfessional(), rules.getCertificate().toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token.split(" ")[1]);
        String json = new ObjectMapper().writeValueAsString(dto);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        // here we get an hashedIndex of the rules of the event.
        ResponseEntity<Integer> hashedIndex = new RestTemplate()
                .postForEntity("http://localhost:8084/api/certificate/getRuleIndex", entity, Integer.class);
        // hashedIndex returns 404 if an error getting the index occurs
        if (hashedIndex.getBody() == 404) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error in generating index!");
        }
        if (hashedIndex.getStatusCode().is2xxSuccessful()) {
            // if the hashIndex is successfully found, we put it in
            // the Event table and send the event to be updated
            List<Rule> rulesList = new ArrayList<>();
            if (hashedIndex.getBody() / 100 == 1) {
                rulesList.add(Rule.SAME_GENDER_TEAMS);
            }
            if (hashedIndex.getBody() / 10 % 10 == 1) {
                rulesList.add(Rule.ONLY_PROFESSIONAL);
            }
            switch (hashedIndex.getBody() % 10) {
                case 1:
                    rulesList.add(Rule.MINIMUM_C4);
                    break;
                case 2:
                    rulesList.add(Rule.MINIMUM_FOURPLUS);
                    break;
                case 3:
                    rulesList.add(Rule.MINIMUM_EIGHTPLUS);
                    break;
                default:
                    break;
            }
            event.setRules(rulesList);
            HttpEntity<Event> entity2 = new HttpEntity<>(event, headers);
            return new RestTemplate().postForEntity("http://localhost:8083/api/event/update", entity2, String.class);
        } else {
            throw new NotFoundException("Incorrectly updated rules!");
        }
    }

    /**
     * Endpoint for deleting events from the database.
     *
     * @param eventId The id of the event that has to be deleted
     * @return A responseEntity with a 200 OK message if the event was deleted successfully
     *         A responseEntity with a 401 UNAUTHORIZED message if the user who sent request
     *         is not the admin of the event
     *         A responseEntity with a 404 NOT_FOUND message if the event was not found
     */
    @DeleteMapping({"/event/delete"})
    public ResponseEntity<String> delete(@RequestBody Long eventId) {
        try {
            Event old = eventService.getEvent(eventId);
            if (!old.getAdmin().equals(auth.getNetId())) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            eventService.deleteEvent(eventId);
            return ResponseEntity.ok("successfully deleted event");
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint for getting all valid the events in the database.
     * We create another Endpoint than getAll activities
     * so we can check if our filtering actually works by comparing two outputs
     *Function firstly send request to Certificate ms to obtain all events which match user preferences
     * and next it check them based on the time constraints.
     *
     * @return The Events as a list of strings
     */
    @GetMapping({"/event/getEvents"})
    public ResponseEntity<String> getEvents(@RequestHeader("Authorization") String token) {
        // checks if the database is not empty
        if (eventService.getAllEvents().size() != 0) {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(token.split(" ")[1]);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            // here we get an indices of the matching event.
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<EventIdsDto> eventIds = restTemplate
                    .exchange("http://localhost:8084/api/certificate/getValidEvents",
                            HttpMethod.GET, entity, EventIdsDto.class);

            if (eventIds.getStatusCode() == HttpStatus.BAD_REQUEST)  {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("There are no events for you to join!");
            }
            Set<Long> setOfIds = new HashSet<>(eventIds.getBody().getIds());
            List<Event> list = eventService.getMatchingEvents(setOfIds);
            // checks if the there are some available activities to join
            if (list.isEmpty())  {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("There are no events for you to join!");
            }
            return ResponseEntity.ok(list.toString());
        } else {
            // else returns BAD_REQUEST
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("There are no events for you to join!");
        }
    }
}

