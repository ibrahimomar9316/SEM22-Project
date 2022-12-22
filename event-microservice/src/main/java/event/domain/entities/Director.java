package event.domain.entities;

import event.domain.enums.EventType;
import event.domain.objects.Participant;
import java.time.LocalDateTime;
import java.util.List;

public class Director {
    public void createEvent(Builder builder, EventType eventType, String admin, LocalDateTime time, List<Participant> participants) {
        builder.setEventType(eventType);
        builder.setAdmin(admin);
        builder.setTime(time);
        builder.setParticipants(participants);
    }
}

