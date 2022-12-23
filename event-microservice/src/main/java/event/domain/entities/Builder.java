package event.domain.entities;

import event.domain.enums.EventType;
import event.domain.objects.Participant;
import java.time.LocalDateTime;
import java.util.List;

public interface Builder {

    public void setEventType(EventType eventType);

    public void setAdmin(String admin);

    public void setTime(LocalDateTime time);

    public void setParticipants(List<Participant> participants);

    public Event build();

}
