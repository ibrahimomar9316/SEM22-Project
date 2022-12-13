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
 * The Service class for managing the events in the H2 DB.
 *
 * <p>It is used by the network controller to manage the
 * database (add, get, edit, remove events). it uses the
 * EventRepository, which is a JpaRepository.</p>
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
    public Event getEvent(Long eventId) throws NotFoundException {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new NotFoundException("Event not found in the database.");
        }
        return eventOptional.get();
    }

    /**
     * Service layer method used for saving an event into the repository.
     * Used when new events are created or when we want to update a new one.
     *
     * @param event the event that we want to save into the repository
     * @return the same event that we saved
     */
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    /**
     * Update event in the database.
     *
     * @param event the event to be updated
     * @return the same event
     */
    public Event updateEvent(Event event) {
        return eventRepository.saveAndFlush(event);
    }

    /**
     * Service layer method used to get all the events, mainly used for
     * debugging or when a user wants to seek an event that they are interested in.
     *
     * @return a list of events
     */
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    /**
     * Service layer method used to get all the events by the admin
     * that created them.
     *
     * @param netId the net id of the admin
     * @return a list of events
     */
    public List<Event> getEventsByAdmin(String netId) {
        return eventRepository.getEventsByAdmin(netId);
    }

    /**
     * Service layer method used to get all the events if a user
     * is participating in them.
     *
     * @param netId the net id of the user
     * @return a list of events
     */
//    public List<Event> getEventsByParticipant(String netId) {
//        return eventRepository.getEventsByParticipant(netId);
//    }

}
