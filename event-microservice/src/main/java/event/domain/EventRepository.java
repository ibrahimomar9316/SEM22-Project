package event.domain;

import event.domain.entities.Event;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Interface for the EventRepository.
 */
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value = "SELECT e FROM Event e WHERE e.eventId=?1")
     Event getEventById(Long eventId);

    @Query(value = "SELECT e FROM Event e WHERE e.admin=?1")
     List<Event> getEventsByAdmin(String admin);

    //TODO: Modify this query

    @Query(value = "SELECT e FROM Event e WHERE e.participants LIKE ?1")
     List<Event> getEventsByParticipant(String participant);
}
