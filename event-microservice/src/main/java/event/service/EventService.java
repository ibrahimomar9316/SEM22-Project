package event.service;

import event.domain.EventRepository;
import event.domain.entities.Event;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * The type Event service.
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EventService {
    private final EventRepository eventRepository;

    /**
     * Service layer method for getting the specific event by their ID.
     *
     * @param eventId - event ID
     * @return - returns a found event.
     * @throws NotFoundException - thrown when event not found
     */
    public Event getevent(Long eventId) throws NotFoundException {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new NotFoundException("Event not found in the database.");
        }
        return eventOptional.get();
    }

    /**
     * Save event event.
     *
     * @param event the event
     * @return the event
     */
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    /**
     * Gets all events.
     *
     * @return the all events
     */
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

}
