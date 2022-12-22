package event.models;

import event.foreigndomain.enums.Certificate;
import lombok.Data;

/**
 * A model used to transmit the rules:
 * <br> <br>
 * - The event ID <br>
 * - A boolean if the event is segregated by gender <br>
 * - A boolean if the event is only available for professional players <br>
 * - The certificate required to be a cox for this event <br>
 * <br>
 * of a new event through the JSON body of the /setRules request.
 */
@Data
public class EventRulesModel {
    private transient  long eventId;
    private transient boolean sameGender;
    private transient boolean professional;
    private transient Certificate certificate;
}
