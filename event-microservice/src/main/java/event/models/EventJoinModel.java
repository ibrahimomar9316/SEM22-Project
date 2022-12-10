package event.models;

import lombok.Data;

/**
 * A model used to transmit the event ID
 * through the JSON body of the /join
 * request.
 */
@Data
public class EventJoinModel {
    long id;
}
