package event.domain.entities;

import event.domain.enums.EventType;
import event.domain.objects.Participant;

import java.time.LocalDateTime;
import java.util.List;

public class EventBuilder implements Builder {
    private transient EventType eventType;
    private transient String admin;
    private transient LocalDateTime time;
    private transient List<Participant> participants;

    @Override
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public void setAdmin(String admin) {
        this.admin = admin;
    }

    @Override
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public Event build() {
        return new Event(eventType, admin, time, participants);
    }
}
