package event.models;

import event.foreigndomain.enums.Certificate;
import lombok.Data;

@Data
public class EventRulesModel {
    private transient  long eventId;
    private transient boolean sameGender;
    private transient boolean professional;
    private transient Certificate certificate;
}
