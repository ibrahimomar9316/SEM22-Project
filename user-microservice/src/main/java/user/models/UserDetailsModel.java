package user.models;

import java.util.List;
import java.time.LocalDateTime;
import lombok.Data;
import user.domain.enums.Certificate;
import user.domain.enums.Gender;
import user.domain.enums.Position;

@Data
public class UserDetailsModel {
    private Gender gender;
    private Position prefPosition;
    private boolean competitive;
    private Certificate certificate;
    private List<LocalDateTime> avDates;
}
