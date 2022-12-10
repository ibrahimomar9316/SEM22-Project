package event.service;

import event.domain.EventRepository;
import event.domain.entities.Event;
import java.util.List;
import java.util.Optional;
import javassist.NotFoundException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
     * Service layer method for getting the specific event by their ID from the repository.
     *
     * @param eventId event ID that we want to get
     * @return the event that we wanted to get
     * @throws NotFoundException exception that is thrown when the event is not found
     */
    public Event getevent(Long eventId) throws NotFoundException {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new NotFoundException("Event not found in the database.");
        }
        return eventOptional.get();
    }

    /**
     * Service layer method used for saving an event into the repository (Used when new events are created or when we
     * want to update a new one.
     *
     * @param event the event that we want to save into the repository
     * @return the same event that we saved
     */
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    /**
     * Service layer method used to get all of the events, mainly used for debugging or when an user wants to seek
     * an event that is interested in.
     *
     * @return a list of all events
     */
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

}
