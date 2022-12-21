package user.datatransferobjects;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserCertificateDtoTest {
    @Test
    public void testUserCertificateDto() {
        boolean male = true;
        boolean competitive = true;
        String position = "forward";
        String certificate = "FIFA";
        UserCertificateDto dto = new UserCertificateDto(male, competitive, position, certificate);

        assertEquals(male, dto.isMale());
        assertEquals(competitive, dto.isCompetitive());
        assertEquals(position, dto.getPosition());
        assertEquals(certificate, dto.getCertificate());
    }
}