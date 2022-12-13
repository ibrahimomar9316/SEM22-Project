package event.domain.entities;

import event.domain.enums.EventType;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import event.domain.enums.Rules;
import event.domain.objects.Participant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Event.
 */
@Entity
@Data
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "events")
@AllArgsConstructor
public class Event {

    /**
     * Values that are stored in the event as an object but also for the DataBase.
     */
    @Id
    @Column(name = "eventId", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long eventId;

    @Column
    private EventType eventType;

    @Column
    private String admin;

    @Column
    @ElementCollection(targetClass = Participant.class)
    private List<Participant> participants;

    @Column
    private LocalDateTime time;

    @Column
    @ElementCollection(targetClass = Rules.class)
    private List<Rules> rules;

    /**
     * Empty constructor for the Event class.
     */
    public Event() {}

    /**
     * Constructor for a new Event.
     *
     * @param eventType    an enum containing the event type (competition/training)
     * @param admin        the netId of the user who is the owner/admin of the event
     *                     being the only person who can edit or remove the event
     * @param time         the date and time of the start of the event
     * @param participants a list containing the participants in the event
     * @param rules        a list containing the requirements the participants need
     *                     to participate in the event
     */
    public Event(EventType eventType, String admin, LocalDateTime time, List<Participant> participants, List<Rules> rules) {
        this.eventType = eventType;
        this.admin = admin;
        this.time = time;
        this.participants = participants;
        this.rules = rules;
    }

    /**
     * Function that returns the number of participants enrolled for an event.
     *
     * @return an int containing the number of participants
     */
    public int numberOfParticipants() {
        return this.participants.size();
    }

    /**
     * Function that creates a string that will be printed after a new event is
     * created by a user.
     *
     * @return a string representing an announcement that a new event was created,
     *          stating the number of participants and who the owner of the event is
     */
    public String toStringNewEvent() {
        return "You created a new event! \nAdministrator: " + admin
                + "\nNumber of participants:" + numberOfParticipants();
    }

    /**
     * Method that converts and event into a string showing what type of event
     * was created and who created it.
     *
     * @return A string representing who created an event
     */
    @Override
    public String toString() {
        return eventType.toString() + " made by " + admin + "at: " + time + "\n" + participants + "\n" + rules;
    }

    public String toStringJoin() {
        return "You have joined event " + eventId + " made by " + admin;
    }

    public String toStringUpdate() {
        return "You have updated event " + eventId;
    }
}
