package user.datatransferobjects;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import user.domain.enums.Gender;

public class UserCertificateDtoTest {
    @Test
    public void testUserCertificateDto() {
        boolean competitive = true;
        String position = "forward";
        String certificate = "FIFA";
        UserCertificateDto dto = new UserCertificateDto(Gender.MALE, competitive, position, certificate);

        assertEquals(Gender.MALE, dto.getGender());
        assertEquals(competitive, dto.isCompetitive());
        assertEquals(position, dto.getPosition());
        assertEquals(certificate, dto.getCertificate());
    }
}