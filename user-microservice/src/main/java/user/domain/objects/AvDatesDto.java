package user.domain.objects;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AvDatesDto implements Serializable {
    private final LocalDateTime dateFrom;
    private final LocalDateTime dateTo;
}
