package user.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import user.domain.enums.Gender;
import user.foreigndomain.enums.Certificate;
import user.foreigndomain.enums.Position;

public class AppUserTest {
    @Test
    public void testAppUser() {
        String netId = "test123";
        Gender gender = Gender.MALE;
        Position prefPosition = Position.COACH;
        boolean competitive = true;
        Certificate certificate = Certificate.NONE;
        LocalDateTime from = LocalDateTime.MIN;
        LocalDateTime to = LocalDateTime.MAX;

        AppUser user = new AppUser(netId);
        user.upDate(
                gender,
                prefPosition,
                competitive,
                certificate,
                from,
                to
        );

        assertEquals(netId, user.getNetId());
        assertEquals(gender, user.getGender());
        assertEquals(prefPosition, user.getPrefPosition());
        assertEquals(competitive, user.isCompetitive());
        assertEquals(certificate, user.getCertificate());
        assertEquals(from, user.getAvailableFrom());
        assertEquals(to, user.getAvailableTo());
    }

    @Test
    public void testDefaultConstructor() {
        AppUser user = new AppUser();
        assertNull(user.getNetId());
        assertNull(user.getGender());
        assertNull(user.getPrefPosition());
        assertEquals(false, user.isCompetitive());
        assertNull(user.getCertificate());
        assertNull(user.getAvailableFrom());
        assertNull(user.getAvailableTo());
    }


    @Test
    public void testEqualsMethod() {
        String netId = "john.doe";
        Gender gender = Gender.MALE;
        Position prefPosition = Position.COACH;
        boolean competitive = true;
        Certificate certificate = Certificate.NONE;
        LocalDateTime from = LocalDateTime.MIN;
        LocalDateTime to = LocalDateTime.MAX;
        AppUser user1 = new AppUser(netId, gender, prefPosition, competitive, certificate, from, to);
        AppUser user2 = new AppUser(netId, gender, prefPosition, competitive, certificate, from, to);

        // user1 and user2 should be equal
        assertEquals(user1, user2);

        // change a field in user2 and verify that user1 and user2 are no longer equal
        user2.setNetId("jane.doe");
        assertNotEquals(user1, user2);
    }
}

