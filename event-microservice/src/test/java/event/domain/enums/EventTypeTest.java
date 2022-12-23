package event.domain.enums;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class EventTypeTest {

    @Test
    void valuesTest() {
        assertThat(EventType.COMPETITION)
                .isEqualTo(EventType.COMPETITION);
        assertThat(EventType.TRAINING)
                .isEqualTo(EventType.TRAINING);
    }
}
