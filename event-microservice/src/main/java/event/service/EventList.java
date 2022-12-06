package event.service;

import event.domain.Event;
import java.util.List;

/**
 * EventList controlling a list of events created.
 */
public class EventList {
    private List<Event> listOfEvens;

    /**
     * Sets list of evens.
     *
     * @param listOfEvens the list of evens
     */
    public void setListOfEvens(List<Event> listOfEvens) {
        this.listOfEvens = listOfEvens;
    }

    /**
     * Gets list of evens.
     *
     * @return the list of evens
     */
    public List<Event> getListOfEvens() {
        return listOfEvens;
    }

    /**
     * Add an event.
     *
     * @param e the event to be added
     */
    public void addEvent(Event e) {
        listOfEvens.add(e);
    }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Available events to join:");
        for (Event listOfEven : listOfEvens) {
            s.append("\n").append(listOfEven.toString());
        }
        return s.toString();
    }
}
