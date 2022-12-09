package event.models;

import event.domain.enums.EventType;
import lombok.Data;

@Data
public class EventCreationModel {
    private EventType eventType;
}
