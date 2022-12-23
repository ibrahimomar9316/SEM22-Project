package event.models;

import event.foreigndomain.enums.Certificate;
import event.foreigndomain.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A model used to transmit the rules:
 * <br> <br>
 * - The event ID <br>
 * - A boolean if the event is segregated by gender <br>
 * - A boolean if the event is only available for professional players <br>
 * - The certificate required to be a cox for this event <br>
 * e
 * of a new event through the JSON body of the /setRules request.
 */
@Data
@AllArgsConstructor
public class EventRulesModel {
    private transient  long eventId;
    private transient Gender genderConstraint;
    private transient boolean professional;
    private transient Certificate certificate;
}
