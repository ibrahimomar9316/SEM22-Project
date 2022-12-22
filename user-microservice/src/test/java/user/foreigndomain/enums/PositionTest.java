package user.foreigndomain.enums;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PositionTest {

    @Test
    void valuesTest() {
        assertThat(Position.COX)
                .isEqualTo(Position.COX);
        assertThat(Position.COACH)
                .isEqualTo(Position.COACH);
        assertThat(Position.STARBOARD_SIDE_ROWER)
                .isEqualTo(Position.STARBOARD_SIDE_ROWER);
        assertThat(Position.SCULLING_ROWER)
                .isEqualTo(Position.SCULLING_ROWER);
        assertThat(Position.PORT_SIDE_ROWER)
                .isEqualTo(Position.PORT_SIDE_ROWER);
    }
}