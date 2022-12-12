package event.domain.entities;

import event.domain.enums.EventType;
import event.foreigndomain.entitites.AppUser;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
    @ElementCollection(targetClass = String.class)
    private List<String> participants;

    @Column
    private Date date;

    @Column
    private Time time;

    @Column
    private transient String rules;

    /**
     * Empty constructor for the Event class.
     */
    public Event() {}

    /**
     * Constructor for a new Event.
     *
     * @param eventType an enum containing the event type (competition/training)
     * @param admin     an AppUser representing who is the admin/owner of an event,
     *                  being the one that can manage or modify the whole event
     */
    public Event(EventType eventType, AppUser admin) {
        this.eventType = eventType;
        this.admin = admin.getNetId();
        participants = new ArrayList<>();
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
                + "\nNumber of participants: " + numberOfParticipants();
    }

    /**
     * Method that converts and event into a string showing what type of event
     * was created and who created it.
     *
     * @return A string representing who created an event
     */
    @Override
    public String toString() {
        String string = eventType.toString().toLowerCase(Locale.ROOT);
        string = Character.toUpperCase(string.charAt(0)) + string.substring(1);
        return string + " Event made by " + admin + ", number of participants: " + numberOfParticipants();
    }

    public String toStringJoin() {
        return "You have joined event " + eventId + " made by " + admin;
    }
}
