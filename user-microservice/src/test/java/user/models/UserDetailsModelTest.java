package user.models;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import user.domain.enums.Gender;
import user.foreigndomain.enums.Certificate;
import user.foreigndomain.enums.Position;



class UserDetailsModelTest {

    @Test
    void testConstructorAndGetters() {
        Gender gender = Gender.MALE;
        Position prefPosition = Position.COX;
        boolean competitive = true;
        Certificate certificate = Certificate.C4;
        LocalDateTime from = LocalDateTime.MIN;
        LocalDateTime to = LocalDateTime.MAX;

        UserDetailsModel userDetailsModel = new UserDetailsModel(gender, prefPosition, competitive, certificate, from, to);

        assertEquals(gender, userDetailsModel.getGender());
        assertEquals(prefPosition, userDetailsModel.getPrefPosition());
        assertEquals(competitive, userDetailsModel.isCompetitive());
        assertEquals(certificate, userDetailsModel.getCertificate());
        assertEquals(from, userDetailsModel.getAvailableFrom());
        assertEquals(to, userDetailsModel.getAvailableTo());

        userDetailsModel.setCompetitive(false);
        assertNotEquals(competitive, userDetailsModel.isCompetitive());
    }
}
