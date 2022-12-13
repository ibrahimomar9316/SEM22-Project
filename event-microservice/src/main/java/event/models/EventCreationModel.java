package event.models;

import event.domain.enums.EventType;
import lombok.Data;

/**
 * A model used to transmit the event type
 * (COMPETITION or TRAINING)
 * through the JSON body of the /create
 * request.
 */
@Data
public class EventCreationModel {
    private EventType eventType;
}
