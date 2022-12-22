package user.models;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import user.domain.enums.Gender;
import user.foreigndomain.enums.Certificate;
import user.foreigndomain.enums.Position;

@Data
public class UserDetailsModel {
    private Gender gender;
    private Position prefPosition;
    private boolean competitive;
    private Certificate certificate;
    private List<LocalDateTime> avDates;
}
