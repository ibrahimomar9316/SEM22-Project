package event.domain.entities;

import commons.BoatMembers;
import event.domain.enums.EventType;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
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
