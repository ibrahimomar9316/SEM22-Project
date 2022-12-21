package user.models;

import lombok.Data;
import user.domain.enums.Certificate;
import user.domain.enums.Gender;
import user.domain.enums.Position;

/**
 * Model used to pass along values between Spring API endpoints.
 */
@Data
public class UserDetailsModel {
    private transient Gender gender;
    private transient Position prefPosition;
    private transient boolean competitive;
    private transient Certificate certificate;
}
