package user.domain.entities;




import org.junit.jupiter.api.Test;
import user.domain.enums.Certificate;
import user.domain.enums.Gender;
import user.domain.enums.Position;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppUserTest {
    @Test
    public void testAppUser() {
        String netId = "test123";
        Gender gender = Gender.MALE;
        Position prefPosition = Position.COACH;
        boolean competitive = true;
        Certificate certificate = Certificate.NONE;
        List<LocalDateTime> avDates = Arrays.asList(LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        AppUser user = new AppUser(netId);
        user.setGender(gender);
        user.setPrefPosition(prefPosition);
        user.setCompetitive(competitive);
        user.setCertificate(certificate);
        user.setAvDates(avDates);

        assertEquals(netId, user.getNetId());
        assertEquals(gender, user.getGender());
        assertEquals(prefPosition, user.getPrefPosition());
        assertEquals(competitive, user.isCompetitive());
        assertEquals(certificate, user.getCertificate());
        assertEquals(avDates, user.getAvDates());
    }

    @Test
    public void testDefaultConstructor() {
        AppUser user = new AppUser();
        assertNull(user.getNetId());
        assertNull(user.getGender());
        assertNull(user.getPrefPosition());
        assertEquals(false, user.isCompetitive());
        assertNull(user.getCertificate());
        assertNull(user.getAvDates());
    }


    @Test
    public void testEqualsMethod() {
        String netId = "john.doe";
        Gender gender = Gender.MALE;
        Position prefPosition = Position.COACH;
        boolean competitive = true;
        Certificate certificate = Certificate.NONE;
        List<LocalDateTime> avDates = new ArrayList<>();
        AppUser user1 = new AppUser(netId, gender, prefPosition, competitive, certificate, avDates);
        AppUser user2 = new AppUser(netId, gender, prefPosition, competitive, certificate, avDates);

        // user1 and user2 should be equal
        assertEquals(user1, user2);

        // change a field in user2 and verify that user1 and user2 are no longer equal
        user2.setNetId("jane.doe");
        assertNotEquals(user1, user2);
    }
}

