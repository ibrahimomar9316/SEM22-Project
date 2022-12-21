package user.models;

import java.time.LocalDateTime;
import java.util.List;
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

    /**
     * Constructor for the UserDetailsModel.
     */
    public UserDetailsModel(Gender gender, Position prefPosition,
                            boolean competitive, Certificate certificate,
                            List<LocalDateTime> avDates) {
        this.gender = gender;
        this.prefPosition = prefPosition;
        this.competitive = competitive;
        this.certificate = certificate;
        this.avDates = avDates;
    }
}
