package event.datatransferobjects;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DTO class to store event ids which later are sent two Event microservice.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventIdsDto {

    private List<Long> ids;

}

