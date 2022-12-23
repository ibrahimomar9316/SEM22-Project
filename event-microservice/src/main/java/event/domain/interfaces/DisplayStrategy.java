package event.domain.interfaces;

import event.domain.entities.Event;
import java.util.List;

/**
 * Interface for the Display Strategy.
 */
public interface DisplayStrategy {
    public List<Event> displayValidEvents(List<Event> eventList);
}
