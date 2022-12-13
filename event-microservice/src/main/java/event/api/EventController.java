package event.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import event.authentication.AuthManager;
import event.domain.entities.Event;
import event.foreigndomain.entitites.AppUser;
import event.models.EventCreationModel;
import event.service.EventService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        Event savedEvent = new Event(request.getEventType(), auth.getNetId(), request.getTime(), request.getParticipants(), request.getRules());

        // Saves event to database using eventService
        eventService.saveEvent(savedEvent);
        // returns OK and a string implemented in Event
        return ResponseEntity.ok(savedEvent.toStringNewEvent());
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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @PostMapping({"/event/join"})
//    public ResponseEntity<String> join(EventJoinModel request) {
//        try {
//            Event event = eventService.getEvent(request.getId());
//            event.getParticipants().add(auth.getNetId());
//            eventService.saveEvent(event);
//            return ResponseEntity.ok(event.toStringJoin());
//        } catch (NotFoundException e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
