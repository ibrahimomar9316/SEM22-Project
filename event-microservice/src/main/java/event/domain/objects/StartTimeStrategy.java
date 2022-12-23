package event.domain.objects;

import event.domain.entities.Event;
import event.domain.interfaces.DisplayStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Concrete Strategy class, sorts events based on the time the event starts.
 * That is, users will first see the events that start at the earliest
 */
public class StartTimeStrategy implements DisplayStrategy {

    /**
     * Displays a list of events sorted by time they start.
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
     * Sorts a list of events by time.
     *
     * @param list the list of events to be sorted
     */
    private void sort(List<Event> list) {
        Collections.sort(list, new EventComparator());
    }

    protected class EventComparator implements Comparator<Event> {

        /**
         * Compares two events based on their time.
         *
         * @param e1 the first event to be compared
         * @param e2 the second event to be compared
         * @return a negative integer if e1 is before e2, a positive integer if e1 is after e2, or 0 if they are equal
         */
        @Override
        public int compare(Event e1, Event e2) {

            if (e1.getTime().isBefore(e2.getTime())) {
                return -1;
            } else if (e1.getTime().isAfter(e2.getTime())) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
