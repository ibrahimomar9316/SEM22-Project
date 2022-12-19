package user.models;

import lombok.Data;
import user.domain.enums.Certificate;
import user.domain.enums.Gender;
import user.domain.enums.Position;

@Data
public class UserDetailsModel {
    private Gender gender;
    private Position prefPosition;
    private String competitive;
    private Certificate certificate;
}
