package event.models;

import event.domain.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A model used to transmit the event ID
 * through the JSON body of the /join
 * request.
 */
@Data
@AllArgsConstructor
public class EventJoinModel {
    private Position position;
    private long eventId;
}
