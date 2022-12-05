package event.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of one type of event: Training Session
 * A training session is more informal than a competition,
 * and it is also administered differently.
 */
public class Training extends Event {

    //TODO: Change admin data type to User
    private String admin;
    private List<String> participants;

    /**
     * Constructor for creating a training-type event.
     * Sets the admin and the list of participants to the ones
     * specified in the method.
     *
     * @param admin the admin of the training session;
     * @param participants the list of participants to the competition;
     */
    public Training(String admin, List<String> participants) {
        this.admin = admin;
        this.participants = participants;
    }

    /**
     * Constructor for creating a training-type event.
     * Sets the admin to the one specified in method signature
     * Creates a new list of participants.
     *
     * @param admin the admin of the training session;
     */
    public Training(String admin) {
        this.admin = admin;
        this.participants = new ArrayList<>();
    }

    /**
     * A simple to string method to show the user the training session.
     * (Implementation might be changed later)
     *
     * @return a string implementation of a training session;
     */
    @Override
    public String toString() {
        return "This is a training session created by: "
            + admin
            + "\n"
            + "Number of participants: "
            + numberOfParticipants();
    }
}
