package event.domain.objects;

import event.domain.entities.Event;
import event.domain.interfaces.DisplayStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Concrete Strategy class, sorts events based on the time they have been added to the database.
 * (Simply based on the id of the event)
 */
public class AddTimeStrategy implements DisplayStrategy {

    /**
     * Displays a list of events sorted by time they have been added/created.
     *
     * @param eventList the list of events to be sorted and displayed
     * @return the sorted list of events
     */
    @Override
    public List<Event> displayValidEvents(List<Event> eventList) {
        if (eventList == null || eventList.isEmpty()) {
            return new ArrayList<>();
        }
        sort(eventList);
        return eventList;
    }

    /**
     * Sorts a list of events based on their IDs.
     *
     * @param list the list of events to be sorted
     */
    private void sort(List<Event> list) {
        Collections.sort(list, new EventComparator());
    }

    protected class EventComparator implements Comparator<Event> {
        /**
         * Compares two events based on their event IDs.
         *
         * @param e1 the first event to be compared
         * @param e2 the second event to be compared
         * @return a negative integer if the event ID of e1 is less than the event ID of e2,
         *         a positive integer if the event ID of e1 is greater than the event ID of e2, or 0 if they are equal
         */
        @Override
        public int compare(Event e1, Event e2) {
            return Long.compare(e1.getEventId(), e2.getEventId());
        }
    }
}
