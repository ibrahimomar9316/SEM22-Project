package user.foreigndomain.enums;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CertificateTest {

    @Test
    void valuesTest() {
        assertThat(Certificate.NONE)
                .isEqualTo(Certificate.NONE);
        assertThat(Certificate.C4)
                .isEqualTo(Certificate.C4);
        assertThat(Certificate.FOURPLUS)
                .isEqualTo(Certificate.FOURPLUS);
        assertThat(Certificate.EIGHTPLUS)
                .isEqualTo(Certificate.EIGHTPLUS);
    }
}