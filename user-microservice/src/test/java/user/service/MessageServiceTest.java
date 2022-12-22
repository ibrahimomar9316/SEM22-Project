package user.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import user.domain.entities.Message;
import user.domain.enums.MessageType;
import user.foreigndomain.enums.Position;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class MessageServiceTest {

    @Autowired
    private MessageService messageService;


    @Test
    void getAllMessages() {
        Message m1 = new Message(1, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2");
        Message m2 = new Message(2, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2");
        messageService.save(m1);
        messageService.save(m2);
        List<Message> all = messageService.getAllMessages();
        assertThat(all.size()).isEqualTo(2);
        assertThat(all.contains(m1)).isTrue();
        assertThat(all.contains(m2)).isTrue();

        Message m3 = new Message(3, 2L, MessageType.JOIN, Position.COACH, "user1", "user2");

        assertThat(all.contains(m3)).isFalse();
    }

    @Test
    void getInbox() {
        Message m1 = new Message(1, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2");
        Message m2 = new Message(2, 2L, MessageType.LEAVE, Position.COACH, "user1", "user3");
        Message m3 = new Message(3, 2L, MessageType.JOIN, Position.COACH, "user1", "user2");
        messageService.save(m1);
        messageService.save(m2);
        messageService.save(m3);
        List<Message> inbox = messageService.getInbox("user2");
        assertThat(inbox.size())
                .isEqualTo(2);
        assertThat(inbox.contains(m1))
                .isTrue();
        assertThat(inbox.contains(m3))
                .isTrue();
        assertThat(inbox.contains(m2))
                .isFalse();
    }

    @Test
    void save() {
        Message m1 = new Message(1, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2");
        messageService.save(m1);
        assertThat(m1)
                .isEqualTo(messageService.getMessage(1));
    }

    @Test
    void getSentMessages() {
        Message m1 = new Message(1, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2");
        Message m2 = new Message(2, 2L, MessageType.LEAVE, Position.COACH, "user2", "user2");
        messageService.save(m1);
        messageService.save(m2);
        List<Message> sent = messageService.getSentMessages("user1");
        assertThat(sent.size())
                .isEqualTo(1);
        assertThat(sent.get(0))
                .isEqualTo(m1);
    }

    @Test
    void getMessage() {
        Message m1 = new Message(1, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2");
        Message m2 = new Message(2, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2");
        messageService.save(m1);
        messageService.save(m2);
        assertThat(m2)
                .isEqualTo(messageService.getMessage(2));
        assertThat(m1)
                .isEqualTo(messageService.getMessage(1));
        assertThat(messageService.getMessage(3))
                .isNull();
    }

    @Test
    void deleteMessage() {
        Message m1 = new Message(1, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2");
        Message m2 = new Message(2, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2");
        messageService.save(m1);
        messageService.save(m2);
        messageService.deleteMessage(1);
        assertThat(messageService.getAllMessages().size())
                .isEqualTo(1);
        assertThat(messageService.getAllMessages().contains(m2))
                .isTrue();
        messageService.deleteMessage(4);
        assertThat(messageService.getAllMessages().size())
                .isEqualTo(1);
    }
}