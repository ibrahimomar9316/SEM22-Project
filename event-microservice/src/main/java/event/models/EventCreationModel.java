package event.models;

import event.domain.enums.EventType;
import event.domain.objects.Participant;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A model used to transmit:
 * <br> <br>
 * - The event type: COMPETITION or TRAINING <br>
 * - The time of the event <br>
 * - The participants of the event which hold filled and unfilled positions <br>
 * - The rules of the event <br>
 * <br>
 * of a new event through the JSON body of the /create request.
 */
@Data
@AllArgsConstructor
public class EventCreationModel {
    private EventType eventType;
    private LocalDateTime time;
    private List<Participant> participants;
}
