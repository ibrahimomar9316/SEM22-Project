package event.models;

import event.domain.enums.EventType;
import event.foreignDomain.entitites.AppUser;
import lombok.Data;

@Data
public class EventCreationModel {
    private AppUser user;
    private EventType eventType;
}
