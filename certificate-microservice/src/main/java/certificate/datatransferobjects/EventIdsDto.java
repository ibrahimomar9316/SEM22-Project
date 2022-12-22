package certificate.datatransferobjects;

import java.util.List;
import lombok.Data;

/**
 * DTO class to store event ids which later are sent two Event microservice.
 */
@Data
public class EventIdsDto {

    private List<Long> ids;

    public EventIdsDto(List<Long> ids) {
        this.ids = ids;
    }
}

