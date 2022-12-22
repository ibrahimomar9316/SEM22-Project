package event.foreigndomain.enums;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class GenderTest {

    @Test
    void valuesTest() {
        assertThat(Gender.MALE)
                .isEqualTo(Gender.MALE);
        assertThat(Gender.FEMALE)
                .isEqualTo(Gender.FEMALE);
    }
}