package user.domain.entities;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import user.domain.enums.MessageType;
import user.foreigndomain.enums.Position;

class MessageTest {

    @Test
    void constructorTest() {
        Message m1 = new Message(2L, MessageType.LEAVE, Position.COACH, "user1", "user2");
        Message m2 = new Message();
        assertThat(m1.getMessageId())
                .isEqualTo(0);
        assertThat(m2.getRecipient())
                .isNull();
    }

    @Test
    void getMessageIdTest() {
        Message m = new Message(1, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2");
        assertThat(m.getMessageId())
                .isEqualTo(1);
    }

    @Test
    void setMessageIdTest() {
        Message m = new Message(44, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2");
        assertThat(m.getMessageId())
                .isEqualTo(44);
        m.setMessageId(33);
        assertThat(m.getMessageId())
                .isEqualTo(33);
    }

    @Test
    void getEventIdTest() {
        Message m = new Message(33, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2");
        assertThat(m.getEventId())
                .isEqualTo(2);
    }

    @Test
    void setEventIdTest() {
        Message m = new Message(33, 88L, MessageType.LEAVE, Position.COACH, "user1", "user2");
        assertThat(m.getEventId())
                .isEqualTo(88L);
        m.setEventId(77L);
        assertThat(m.getEventId())
                .isEqualTo(77L);
    }

    @Test
    void getTypeTest() {
        Message m = new Message(33, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2");
        assertThat(m.getType())
                .isEqualTo(MessageType.LEAVE);
    }

    @Test
    void setTypeTest() {
        Message m = new Message(33, 2L, MessageType.ACCEPTED, Position.COACH, "user1", "user2");
        assertThat(m.getType())
                .isEqualTo(MessageType.ACCEPTED);
        m.setType(MessageType.DENIED);
        assertThat(m.getType())
                .isEqualTo(MessageType.DENIED);
    }

    @Test
    void getPositionTest() {
        Message m = new Message(33, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2");
        assertThat(m.getPosition())
                .isEqualTo(Position.COACH);
    }

    @Test
    void setPositionTest() {
        Message m = new Message(33, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2");
        assertThat(m.getPosition())
                .isEqualTo(Position.COACH);
        m.setPosition(Position.COX);
        assertThat(m.getPosition())
                .isEqualTo(Position.COX);
    }

    @Test
    void getSenderTest() {
        Message m = new Message(33, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2");
        assertThat(m.getSender())
                .isEqualTo("user1");
    }

    @Test
    void setSenderTest() {
        Message m = new Message(33, 2L, MessageType.LEAVE, Position.COACH, "user1s", "user2");
        assertThat(m.getSender())
                .isEqualTo("user1s");
        m.setSender("kekw");
        assertThat(m.getSender())
                .isEqualTo("kekw");
    }

    @Test
    void getRecipientTest() {
        Message m = new Message(33, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2");
        assertThat(m.getRecipient())
                .isEqualTo("user2");
    }

    @Test
    void setRecipientTest() {
        Message m = new Message(33, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2ss");
        assertThat(m.getRecipient())
                .isEqualTo("user2ss");
        m.setRecipient("nnn");
        assertThat(m.getRecipient())
                .isEqualTo("nnn");
    }

    @Test
    void testEqualsTest() {
        Message m1 = new Message(33, 4L, MessageType.LEAVE, Position.COACH, "user1", "user2ss");
        Message m2 = new Message(33, 4L, MessageType.LEAVE, Position.COACH, "user1", "user2ss");
        Message m3 = new Message(53, 2L, MessageType.ACCEPTED, Position.SCULLING_ROWER, "332", "u");
        assertThat(m1)
                .isEqualTo(m1);
        assertThat(m1)
                .isEqualTo(m2);
        assertThat(m1)
                .isNotEqualTo(m3);
    }

    @Test
    void testHashCodeTest() {
        Message m1 = new Message(33, 4L, MessageType.LEAVE, Position.COACH, "user1", "user2ss");
        Message m2 = new Message(33, 4L, MessageType.LEAVE, Position.COACH, "user1", "user2ss");
        Message m3 = new Message(53, 2L, MessageType.ACCEPTED, Position.SCULLING_ROWER, "332", "u");
        assertThat(m1.hashCode())
                .isEqualTo(m1.hashCode());
        assertThat(m1.hashCode())
                .isEqualTo(m2.hashCode());
        assertThat(m1.hashCode())
                .isNotEqualTo(m3.hashCode());
    }

    @Test
    void testToStringTest() {
        Message m = new Message(33, 4L, MessageType.LEAVE, Position.COACH, "user1", "user2ss");
        String result = m.toString();
        assertThat(result)
                .contains("messageId=33");
        assertThat(result)
                .contains("eventId=4");
        assertThat(result)
                .contains("type=LEAVE");
        assertThat(result)
                .contains("position=COACH");
        assertThat(result)
                .contains("sender=user1");
        assertThat(result)
                .contains("recipient=user2ss");
    }
}