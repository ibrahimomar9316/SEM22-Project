package user.models;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import user.domain.enums.Gender;
import user.domain.objects.AvDates;
import user.foreigndomain.enums.Certificate;
import user.foreigndomain.enums.Position;



class UserDetailsModelTest {

    @Test
    void testConstructorAndGetters() {
        Gender gender = Gender.MALE;
        Position prefPosition = Position.COX;
        boolean competitive = true;
        Certificate certificate = Certificate.C4;
        List<AvDates> avDates = new ArrayList<>();

        UserDetailsModel userDetailsModel = new UserDetailsModel(gender, prefPosition, competitive, certificate, avDates);

        assertEquals(gender, userDetailsModel.getGender());
        assertEquals(prefPosition, userDetailsModel.getPrefPosition());
        assertEquals(competitive, userDetailsModel.isCompetitive());
        assertEquals(certificate, userDetailsModel.getCertificate());
        assertEquals(avDates, userDetailsModel.getAvDates());

        userDetailsModel.setCompetitive(false);
        assertNotEquals(competitive, userDetailsModel.isCompetitive());
    }
}