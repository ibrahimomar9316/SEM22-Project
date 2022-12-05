package event.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of one type of event: Competition.
 * A competition may have different rules and selection procedures
 * compared to a training routine. It is also managed differently.
 */
public class Competition extends Event {

    //TODO: Change admin data type to User
    private String admin;
    private List<String> participants;

    /**
     * Constructor for creating a competition-type event.
     * Sets the admin and the list of participants to the ones
     * specified in the method.
     */
    public Competition(String admin, List<String> participants) {
        this.admin = admin;
        this.participants = participants;
    }

    /**
     * Constructor for creating a competition-type event.
     * Sets the admin to the one specified in method signature
     * Creates a new list of participants.
     */
    public Competition(String admin) {
        this.admin = admin;
        this.participants = new ArrayList<>();
    }

    /**
     * A simple to string method to show the user the competition.
     * (Implementation might be changed later)
     */
    @Override
    public String toString() {
        return "This is a competitive event created by: "
            + admin
            + "\n"
            + "Number of participants: "
            + numberOfParticipants();
    }
}
