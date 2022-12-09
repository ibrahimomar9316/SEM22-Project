package event.domain.entities;

import event.domain.enums.EventType;
import event.foreigndomain.entitites.AppUser;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public Event() {}

    /**
     * Instantiates a new Event.
     *
     * @param eventType the event type
     * @param admin     the admin
     */
    public Event(EventType eventType, AppUser admin) {
        this.eventType = eventType;
        this.admin = admin.getNetId();
        participants = new ArrayList<>();
    }

    /**
     * Number of participants int.
     *
     * @return the int
     */
    public int numberOfParticipants() {
        return this.participants.size();
    }

    public String toStringNewEvent() {
        return "You created a new event! \nAdministrator: " + admin
                + "\nNumber of participants:" + numberOfParticipants();
    }

    @Override
    public String toString() {
        return eventType.toString() + " made by " + admin;
    }

    public String toStringJoin() {
        return "You have joined event " + eventId + " made by " + admin;
    }
}
