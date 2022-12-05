package event.domain;

import commons.BoatMembers;
import java.util.List;
import java.util.Objects;

/**
 * A abstract class representing the general concept of an Event.
 * It is used to store data regarding the event creator or admin and
 * the list of participants to that event.
 */
public abstract class Event {

    //TODO: Change admin data type to User
    private String admin;
    private List<String> participants;
    private List<BoatMembers> allBoatMembersList;

    /**
     * A getter returning the list of all boats and their member configuration.
     */
    public List<BoatMembers> getAllBoatMembersList() {
        return allBoatMembersList;
    }

    /**
     * A setter for the list of all boats with their corresponding members.
     */
    public void setAllBoatMembersList(List<BoatMembers> allBoatMembersList) {
        this.allBoatMembersList = allBoatMembersList;
    }

    /**
     * A getter returning the private attribute "admin".
     */
    public String getAdmin() {
        return admin;
    }

    /**
     * A getter returning the list of participants.
     */
    public List<String> getParticipants() {
        return participants;
    }

    /**
     * A setter that changes the current admin of the event.
     */
    public void setAdmin(String admin) {
        this.admin = admin;
    }

    /**
     * A setter that changes the list of participants to that event.
     */
    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    /**
     * This adds a new participant to the event list.
     */
    public void addParticipant(String participant) {
        this.participants.add(participant);
    }

    /**
     * This returns the number of participants currently attending the event.
     */
    public int numberOfParticipants() {
        return this.participants.size();
    }

    /**
     * Overwritten equals method. It checks if two events are equal by
     * comparing their admin and the list of their participants.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        Event event = (Event) o;
        return Objects.equals(admin, event.admin)
            && Objects.equals(participants, event.participants)
            && Objects.equals(allBoatMembersList, event.allBoatMembersList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(admin, participants);
    }

}
