package event.api;

import event.domain.Competition;
import event.domain.Event;
import event.service.EventList;
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
    private transient EventList eventList;
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
     * @param user the event owner
     * @return the response entity
     */
    @PostMapping({"/create"})
    public ResponseEntity<EventList> saveRole(@RequestBody String user) {
        Event event = new Competition(user);
        eventList.addEvent(event);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
