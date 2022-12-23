package event.service;

import event.domain.EventRepository;
import event.domain.entities.Event;
import event.domain.enums.EventType;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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
     * @return the event that was saved
     */
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    /**
     * Update event in the database.
     *
     * @param event the event to be updated
     * @return the event that was updated
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
    public List<Event> getEventsByParticipant(String netId) {
        return eventRepository.getEventsByParticipant(netId);
    }

    /**
     * Service layer method used to delete events from the database.
     *
     * @param id The internal id of the event in the database that has to be deleted
     */
    public void deleteEvent(long id) {
        eventRepository.deleteById(id);
    }

    /**
     * Service layer method used to get all the matching events.
     * Right now only checks  time constrains but can be developed to check all others restrictions.
     *
     * @return a list of events
     */
    public List<Event> getMatchingEvents(Set<Long> ids) {
        List<Event> list = eventRepository.findAll();
        return list.stream().filter(x -> ids.contains(x.getEventId())
                && checkTimeConstraints(x))
                .collect(Collectors.toList());
    }


    /**
     * Helper method for checking if event meets out event time constrains:
     * we cannot join a Training if it starts within half an hour.
     * we cannot join a Competition competitions that start within that day.
     */
    private boolean checkTimeConstraints(Event event) {
        LocalDateTime now = LocalDateTime.now();
        if (event.getEventType().equals(EventType.TRAINING)) {
            Duration difference = Duration.between(now, event.getTime());
            long minutes = difference.toMinutes();
            return minutes >= 30;
        } else {
            Duration difference = Duration.between(now, event.getTime());
            long minutes = difference.toHours();
            return minutes >= 24;
        }
    }

}
