package event.foreigndomain.enums;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class MessageTypeTest {

    @Test
    void valuesTest() {
        assertThat(MessageType.ACCEPTED)
                .isEqualTo(MessageType.ACCEPTED);
        assertThat(MessageType.DENIED)
                .isEqualTo(MessageType.DENIED);
        assertThat(MessageType.LEAVE)
                .isEqualTo(MessageType.LEAVE);
        assertThat(MessageType.JOIN)
                .isEqualTo(MessageType.JOIN);
    }

}