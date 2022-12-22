package event.domain.entities;

import event.domain.enums.EventType;
import event.domain.objects.Participant;
import java.time.LocalDateTime;
import java.util.List;

public class Director {
    /**
     * Creates a new event using the provided builder, event type, admin, time, and list of participants.
     *
     * @param builder the builder object used to create the event
     * @param eventType the type of event to be created
     * @param admin the admin responsible for creating the event
     * @param time the time at which the event will occur
     * @param participants the list of participants in the event
     */
    public void createEvent(Builder builder, EventType eventType, String admin, LocalDateTime time,
                            List<Participant> participants) {
        builder.setEventType(eventType);
        builder.setAdmin(admin);
        builder.setTime(time);
        builder.setParticipants(participants);
    }
}

