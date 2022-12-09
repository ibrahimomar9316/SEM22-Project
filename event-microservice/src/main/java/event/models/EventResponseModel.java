package event.models;

import event.domain.entities.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model representing an event creation response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventResponseModel {
    Event event;
}
