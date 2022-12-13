package event.domain.objects;

import event.domain.enums.Position;
import java.util.Objects;
import javax.persistence.Embeddable;


@Embeddable
public class Participant {

    private Position position;

    private transient String netId;


    /**
     * Empty constructor for h2 database.
     */
    public Participant() {}


    /**
     * Constructor for a participant which is not yet filled in.
     *
     * @param position The position the participant fills in the event
     */
    public Participant(Position position) {
        this.position = position;
    }


    /**
     * Constructor for a participant which is filled in.
     *
     * @param position The position the participant fills in the event
     * @param netId The netId of the participant
     */
    public Participant(Position position, String netId) {
        this.position = position;
        this.netId = netId;
    }


    /**
     * Getter for the position of a participant.
     *
     * @return The position of the participant
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Setter for the position of a participant.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Getter for the netId of a participant.
     */
    public void setParticipant(String netId) {
        this.netId = netId;
    }

    /**
     * Setter for the netId of a participant.
     */
    public String getParticipant() {
        return netId;
    }

    /**
     * Equals method for testing equality.
     *
     * @param o The object to compare with
     * @return True if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Participant)) {
            return false;
        }

        Participant that = (Participant) o;

        if (position != that.position) {
            return false;
        }
        return Objects.equals(netId, that.netId);
    }

    /**
     * Hash-method for participants.
     *
     * @return Hash of the participant object
     */
    @Override
    public int hashCode() {
        int result = position != null ? position.hashCode() : 0;
        result = 31 * result + (netId != null ? netId.hashCode() : 0);
        return result;
    }

    /**
     * Default toString method.
     *
     * @return A string representing the participant
     */
    @Override
    public String toString() {
        return position + ": " + netId;
    }
}
