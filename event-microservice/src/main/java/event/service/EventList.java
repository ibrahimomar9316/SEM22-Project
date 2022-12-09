package event.service;

import event.domain.entities.Event;
import java.util.List;

/**
 * EventList controlling a list of events created.
 */
public class EventList {
    private transient List<Event> listOfEvents;

    /**
     * Sets list of evens.
     *
     * @param listOfEvents the list of evens
     */
    public void setListOfEvens(List<Event> listOfEvents) {
        this.listOfEvents = listOfEvents;
    }

    /**
     * Gets list of evens.
     *
     * @return the list of events
     */
    public List<Event> getListOfEvents() {
        return listOfEvents;
    }

    /**
     * Add an event.
     *
     * @param e the event to be added
     */
    public void addEvent(Event e) {
        listOfEvents.add(e);
    }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Available events to join:");
        for (Event listOfEven : listOfEvents) {
            s.append("\n").append(listOfEven.toString());
        }
        return s.toString();
    }
}
