package event.domain;

import event.domain.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value = "SELECT e FROM Event e WHERE e.eventId=?1")
     Event getEventById(Long eventId);
}
