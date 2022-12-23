package certificate.datatransferobjects;

import static org.assertj.core.api.Assertions.assertThat;

import certificate.domain.enums.Gender;
import org.junit.jupiter.api.Test;

class RuleDtoTest {

    @Test
    void getAndSetCertificate() {
        RuleDto dto = new RuleDto(2L, Gender.MALE, true, "C4");
        assertThat(dto.getCertificate()).isEqualTo("C4");

        dto.setCertificate("NONE");
        assertThat(dto.getCertificate()).isEqualTo("NONE");
    }

    @Test
    void getAndSetEventId() {
        RuleDto dto = new RuleDto(2L, Gender.MALE, true, "C4");
        assertThat(dto.getEventId()).isEqualTo(2L);

        dto.setEventId(3L);
        assertThat(dto.getEventId()).isEqualTo(3L);
    }

    @Test
    void getAndSetGenderConstraint() {
        RuleDto dto = new RuleDto(2L, Gender.MALE, true, "C4");
        assertThat(dto.getGenderConstraint()).isEqualTo(Gender.MALE);

        dto.setGenderConstraint(Gender.FEMALE);
        assertThat(dto.getGenderConstraint()).isEqualTo(Gender.FEMALE);
    }

    @Test
    void getAndSetPro() {
        RuleDto dto = new RuleDto(2L, Gender.MALE, true, "C4");
        assertThat(dto.isPro()).isTrue();

        dto.setPro(false);
        assertThat(dto.isPro()).isFalse();
    }

}