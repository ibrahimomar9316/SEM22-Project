package certificate.datatransferobjects;

import static org.assertj.core.api.Assertions.assertThat;

import certificate.domain.enums.Gender;
import org.junit.jupiter.api.Test;

class UserCertificateDtoTest {

    @Test
    void getAndSetGender() {
        UserCertificateDto dto = new UserCertificateDto(Gender.MALE, true, "COACH", "C4");
        assertThat(dto.getGender()).isEqualTo(Gender.MALE);

        dto.setGender(Gender.FEMALE);
        assertThat(dto.getGender()).isEqualTo(Gender.FEMALE);
    }

    @Test
    void isAndSetCompetitive() {
        UserCertificateDto dto = new UserCertificateDto(Gender.MALE, true, "COACH", "C4");
        assertThat(dto.isCompetitive()).isTrue();

        dto.setCompetitive(false);
        assertThat(dto.isCompetitive()).isFalse();
    }

    @Test
    void getPosition() {
        UserCertificateDto dto = new UserCertificateDto(Gender.MALE, true, "COACH", "C4");
        assertThat(dto.getPosition()).isEqualTo("COACH");

        dto.setPosition("COX");
        assertThat(dto.getPosition()).isEqualTo("COX");
    }

    @Test
    void getCertificate() {
        UserCertificateDto dto = new UserCertificateDto(Gender.MALE, true, "COACH", "C4");
        assertThat(dto.getCertificate()).isEqualTo("C4");

        dto.setCertificate("NONE");
        assertThat(dto.getCertificate()).isEqualTo("NONE");
    }
}