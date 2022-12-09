package event.api;

import event.domain.entities.Event;
import event.models.EventCreationModel;
import event.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * Api endpoint that creates a new event (boilerplate).
     *
     * @param createModel
     * @return the response entity
     */
    @PostMapping({"/create"})
    public ResponseEntity<EventService> saveRole(@RequestBody EventCreationModel createModel) {
        Event savedEvent = new Event(createModel.getEventType(), createModel.getUser());
        eventService.saveEvent(savedEvent);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
