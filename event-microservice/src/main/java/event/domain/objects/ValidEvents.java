package event.domain.objects;

import event.domain.entities.Event;
import event.domain.interfaces.DisplayStrategy;
import java.util.List;

/**
 * A class (The Context) for displaying a list of events using a specified display strategy.
 */
public class ValidEvents {
    private transient DisplayStrategy displayStrategy;
    private transient List<Event> events;

    /**
     * Constructs a new ValidEvents instance.
     *
     * @param displayStrategy the display strategy to be used
     * @param events the list of events to be displayed
     */
    public ValidEvents(DisplayStrategy displayStrategy, List<Event> events) {
        this.displayStrategy = displayStrategy;
        this.events = events;
    }

    /**
     * Displays the list of events using the specified display strategy.
     *
     * @return the list of events
     */
    public List<Event> displayValidEvents() {
        return this.displayStrategy.displayValidEvents(this.events);
    }
}
