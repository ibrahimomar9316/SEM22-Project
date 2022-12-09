package event.api;

import event.authentication.AuthManager;
import event.domain.entities.Event;
import event.foreigndomain.entitites.AppUser;
import event.models.EventCreationModel;
import event.models.EventJoinModel;
import event.service.EventService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * The type Event controller.
 */
@RestController
@RequestMapping({"/api"})
public class EventController {
    private transient EventService eventService;
    @Autowired
    private RestTemplate restTemplate;

    private final transient AuthManager auth;

    /**
     * Instantiates a new controller.
     *
     * @param eventService the event service
     * @param auth         the auth manager
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
     * @param request the create model
     * @return the response entity
     */
    @PostMapping({"/event/create"})
    public ResponseEntity<String> create(
            @RequestBody EventCreationModel request) {
        Event savedEvent = new Event(request.getEventType(), new AppUser(auth.getNetId()));
        eventService.saveEvent(savedEvent);
        return ResponseEntity.ok(savedEvent.toStringNewEvent());
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @GetMapping({"/event/getAll"})
    public ResponseEntity<String> getAll() {
        if (eventService.getAllEvents().size() != 0) {
            return ResponseEntity.ok(eventService.getAllEvents().toString());
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Join response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping({"/event/join"})
    public ResponseEntity<String> join(EventJoinModel request) {
        try {
            Event event = eventService.getevent(request.getId());
            event.getParticipants().add(auth.getNetId());
            eventService.saveEvent(event);
            return ResponseEntity.ok(event.toStringJoin());
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
