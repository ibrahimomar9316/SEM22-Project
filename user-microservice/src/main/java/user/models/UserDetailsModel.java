package user.models;

import lombok.Data;
import user.domain.enums.Certificate;
import user.domain.enums.Gender;
import user.domain.enums.Position;

@Data
public class UserDetailsModel {
    private transient Gender gender;
    private transient Position prefPosition;
    private transient boolean competitive;
    private transient Certificate certificate;
}
