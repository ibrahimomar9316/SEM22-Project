package event.domain.entities;

import commons.BoatMembers;
import event.domain.enums.EventType;
import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;

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
    private List<String> participants;

    @Column
    private Date date;

    @Column
    private Time time;

    @Column
    private List<BoatMembers> allBoatMembersList;

    @Column
    private transient String rules;

    public Event(String admin) {
        this.admin = admin;
    }

    public int numberOfParticipants() {
        return this.participants.size();
    }

}
