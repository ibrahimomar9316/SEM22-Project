package user.models;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import user.domain.enums.Gender;
import user.foreigndomain.enums.Certificate;
import user.foreigndomain.enums.Position;

/**
 * Model used to pass along values between Spring API endpoints.
 */
@Data
@AllArgsConstructor
public class UserDetailsModel {
    private transient Gender gender;
    private transient Position prefPosition;
    private transient boolean competitive;
    private transient Certificate certificate;
    private transient LocalDateTime availableFrom;
    private transient LocalDateTime availableTo;
}
