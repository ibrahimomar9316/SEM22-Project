package user.datatransferobjects;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityDto {
    private transient LocalDateTime availableFrom;
    private transient LocalDateTime availableTo;

}
