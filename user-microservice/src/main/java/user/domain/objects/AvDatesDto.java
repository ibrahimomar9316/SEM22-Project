package user.domain.objects;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AvDatesDto implements Serializable {
    private final LocalDateTime dateFrom;
    private final LocalDateTime dateTo;

    public static final long serialVersionUID = 4328743;
}
