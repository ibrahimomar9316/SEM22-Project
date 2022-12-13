package event.api;

import event.authentication.AuthManager;
import event.domain.entities.Event;
import event.domain.objects.Participant;
import event.models.EventCreationModel;
import event.models.EventJoinModel;
import event.service.EventService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

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
public class EventController {

    @Autowired
    private RestTemplate restTemplate;

    // This is used to set, get and check for events in
    // the H2 repo
    private transient EventService eventService;

    // This is used and initiated to check if the token is
    // valid and to get the netID
    private final transient AuthManager auth;

    /**
     * Instantiates a new EventController.
     *
     * @param eventService the event service
     *                     This class controls the interaction
     *                     between the Event repository and the
     *                     JSON interface.
     * @param auth         the auth manager
     *                     This verifies the token and also gets
     *                     the netID of the verified user from their
     *                     token
     */
    @Autowired
    public EventController(EventService eventService, AuthManager auth) {
        this.eventService = eventService;
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
        if (request.getEventType() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid JSON or event type!");
        }
        Event savedEvent = new Event(request.getEventType(),
                auth.getNetId(),
                request.getTime(),
                request.getParticipants(),
                request.getRules());

        // Saves event to database using eventService
        eventService.saveEvent(savedEvent);
        // returns OK and a string implemented in Event
        return ResponseEntity.ok(savedEvent.toString());
    }

    /**
     * Endpoint for getting all the events in the database.
     *
     * <p>This does not implement any filtering of events</p>
     * TODO: Implement filtering according to Certificate and rules/requirements
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
     * @return one of three messages and either 200 or 400
     */
    @PostMapping({"/event/join"})
    public ResponseEntity<String> join(@RequestBody EventJoinModel request) {
        try {
            Event event = eventService.getEvent(request.getEventId());
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
                    .filter(x -> x.getPosition() == request.getPosition() && x.getNetId() == null)
                    .collect(Collectors.toList());
            if (participants.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("This position is already filled");
            }
            participants.get(0).setNetId(netId);

            eventService.updateEvent(event);
            return ResponseEntity.ok("You have joined event " + event.getEventId()
                    + " made by " + event.getAdmin());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("There is no event that has that ID!");
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
     * if the user has successfully removed from the event.
     * If some error occurs, it returns 400 and a
     * message conveying the error.</p>
     *
     * @param request a model containing the id of the event as a long
     * @return one of three messages and either 200 or 400
     */
    @PostMapping({"/event/leave"})
    public ResponseEntity<String> leave(@RequestBody EventJoinModel request) {
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
            participant.get(0).setNetId(null);
            eventService.updateEvent(event);
            return ResponseEntity.ok("You have left event " + event.getEventId()
                    + " made by " + event.getAdmin());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("There is no event that has that ID!");
        }
    }

    /**
     * This endpoint is used my an admin of the event to see
     * the list of events they created.
     * TODO: show notifications for each event.
     *
     * @return the list of events the admin created or 400 if
     *         they did not create any event.
     */
    @GetMapping({"/event/myCreatedEvents"})
    public ResponseEntity<String> myCreatedEvents() {
        // checks if the database is not empty
        if (eventService.getEventsByAdmin(auth.getNetId()).size() != 0) {
            return ResponseEntity.ok(eventService.getEventsByAdmin(auth.getNetId()).toString());
        } else {
            // else returns BAD_REQUEST
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("You have not created an event!");
        }
    }

    /**
     * This endpoint is used my a rower to see the list
     * of events they joined.
     * TODO: show notifications for each event.
     *
     * @return the list of events the user has joined or 400
     *         if they did not join any event.
     */
//    @GetMapping({"/event/myJoinedEvents"})
//    public ResponseEntity<String> myJoinedEvents() {
//        // checks if the database is not empty
//        if (eventService.getEventsByParticipant(auth.getNetId()).size() != 0) {
//            return ResponseEntity.ok(eventService.getEventsByParticipant(auth.getNetId()).toString());
//        } else {
//            // else returns BAD_REQUEST
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body("You have not joined any event!");
//        }
//    }

    /**
     * Endpoint for updating events. Only admins of their own events are able to update their events
     * no one else can. All fields except the id can be updated / changed.
     *
     * @param event The new updated event that has to be stored in the database
     * @return A responseEntity with a 200 OK message if the event was indeed updated
     *         A responseEntity with a 401 UNAUTHORIZED message if the user who sent request
     *         is not the admin of the un-updated event
     *         A responseEntity with a 400 BAD_REQUEST message if the event was not found
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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
