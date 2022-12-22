package certificate.datatransferobjects;

import java.util.List;
import lombok.Data;

@Data
public class EventIdsDto {

    private List<Long> ids;

    public EventIdsDto(List<Long> ids) {
        this.ids = ids;
    }
}

