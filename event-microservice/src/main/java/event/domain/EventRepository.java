package event.domain;

import event.domain.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Interface for the EventRepository.
 */
public interface EventRepository extends JpaRepository<Event, Long> {

    /**
     * Query to find an event by its id.
     *
     * @param eventId The id of the event that has to be found
     * @return The found event
     */
    @Query(value = "SELECT e FROM Event e WHERE e.eventId=?1")
    Event getEventById(Long eventId);

}
