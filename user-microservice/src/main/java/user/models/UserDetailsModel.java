package user.models;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import user.domain.enums.Gender;
import user.domain.objects.AvDates;
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
    private transient List<AvDates> avDates;

}
