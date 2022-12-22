package user.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.ConnectException;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import user.authentication.AuthManager;
import user.authentication.JwtAuthenticationEntryPoint;
import user.authentication.JwtRequestFilter;
import user.authentication.JwtTokenVerifier;
import user.domain.entities.Message;
import user.domain.enums.MessageType;
import user.foreigndomain.enums.Position;
import user.service.EventService;
import user.service.MessageService;


@SpringBootTest
class MessageControllerTest {

    @Autowired
    private MessageController messageController;

    @MockBean
    private MessageService messageService;

    @MockBean
    private EventService eventService;

    @MockBean
    private AuthManager authManager;

    @MockBean
    private JwtTokenVerifier jwtTokenVerifier;

    @MockBean
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @Test
    void getAllMessages() {
        Message m1 = new Message(1, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2");
        Message m2 = new Message(2, 2L, MessageType.LEAVE, Position.COACH, "user1", "user3");
        List<Message> all = List.of(m1, m2);
        when(messageService.getAllMessages()).thenReturn(all);

        ResponseEntity<List<Message>> result = messageController.getAllMessages();
        assertThat(Objects.requireNonNull(result.getBody()).contains(m1)).isTrue();
        assertThat(result.getBody().size()).isEqualTo(2);
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void getMessages() {
        Message m1 = new Message(1, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2");
        Message m3 = new Message(2, 2L, MessageType.ACCEPTED, Position.COACH, "user1", "user3");
        when(messageService.getInbox("user2")).thenReturn(List.of(m1, m3));
        when(authManager.getNetId()).thenReturn("user2");

        ResponseEntity<List<Message>> result = messageController.getMessages();
        assertThat(Objects.requireNonNull(result.getBody()).contains(m1)).isTrue();
        assertThat(result.getBody().size()).isEqualTo(2);
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        Message m2 = new Message(2, 2L, MessageType.JOIN, Position.COACH, "user2", "user3");
        assertThat(result.getBody().contains(m2)).isFalse();
    }

    @Test
    void sendMessage() {
        Message m1 = new Message(1, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2");
        when(messageService.save(m1)).thenReturn(m1);

        ResponseEntity<String> result = messageController.sendMessage(m1);
        assertThat(result.getBody()).isEqualTo("Message successfully sent!");
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void getSentMessages() {
        Message m1 = new Message(1, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2");
        Message m3 = new Message(1, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2");
        when(messageService.getSentMessages("user1")).thenReturn(List.of(m1, m3));
        when(authManager.getNetId()).thenReturn("user1");

        ResponseEntity<List<Message>> result = messageController.getSentMessages();
        assertThat(Objects.requireNonNull(result.getBody()).contains(m1)).isTrue();
        assertThat(result.getBody().size()).isEqualTo(2);
        Message m2 = new Message(2, 2L, MessageType.LEAVE, Position.COACH, "user2", "user3");
        assertThat(result.getBody().contains(m2)).isFalse();
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void acceptRequest() throws ConnectException {
        Message m1 = new Message(1, 2L, MessageType.JOIN, Position.COACH, "user1", "user2");
        Message m2 = new Message(2, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2");

        when(messageService.getMessage(3)).thenReturn(null);
        ResponseEntity<String> result1 = messageController.acceptRequest("token", 3);
        assertThat(result1.getStatusCodeValue()).isEqualTo(404);

        when(messageService.getMessage(1)).thenReturn(m1);
        when(authManager.getNetId()).thenReturn("user3");
        ResponseEntity<String> result2 = messageController.acceptRequest("token", 1);
        assertThat(result2.getStatusCodeValue()).isEqualTo(401);

        when(messageService.getMessage(2)).thenReturn(m2);
        when(authManager.getNetId()).thenReturn("user2");
        ResponseEntity<String> result3 = messageController.acceptRequest("token", 2);
        assertThat(result3.getStatusCodeValue()).isEqualTo(400);

        when(eventService.acceptJoin("token", m1)).thenReturn(HttpStatus.SERVICE_UNAVAILABLE);
        ResponseEntity<String> result4 = messageController.acceptRequest("token", 1);
        assertThat(result4.getStatusCodeValue()).isEqualTo(503);

        when(eventService.acceptJoin("token", m1)).thenReturn(HttpStatus.OK);
        ResponseEntity<String> result5 = messageController.acceptRequest("token", 1);
        assertThat(result5.getStatusCodeValue()).isEqualTo(200);

        Mockito.verify(messageService, atLeastOnce()).deleteMessage(1);

        when(eventService.acceptJoin("token", m1)).thenThrow(ConnectException.class);
        ResponseEntity<String> result6 = messageController.acceptRequest("token", 1);
        assertThat(result6.getStatusCodeValue()).isEqualTo(503);
    }

    @Test
    void denyRequest() {
        Message m1 = new Message(1, 2L, MessageType.JOIN, Position.COACH, "user1", "user2");
        Message m2 = new Message(2, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2");

        when(messageService.getMessage(3)).thenReturn(null);
        ResponseEntity<String> result1 = messageController.denyRequest(3);
        assertThat(result1.getStatusCodeValue()).isEqualTo(404);

        when(messageService.getMessage(1)).thenReturn(m1);
        when(authManager.getNetId()).thenReturn("user3");
        ResponseEntity<String> result2 = messageController.denyRequest(1);
        assertThat(result2.getStatusCodeValue()).isEqualTo(401);

        when(messageService.getMessage(2)).thenReturn(m2);
        when(authManager.getNetId()).thenReturn("user2");
        ResponseEntity<String> result3 = messageController.denyRequest(2);
        assertThat(result3.getStatusCodeValue()).isEqualTo(400);

        ResponseEntity<String> result4 = messageController.denyRequest(1);
        assertThat(result4.getStatusCodeValue()).isEqualTo(200);

        Mockito.verify(messageService, atLeastOnce()).deleteMessage(1);
    }

    @Test
    void deleteMessage() {
        Message m1 = new Message(1, 2L, MessageType.JOIN, Position.COACH, "user1", "user2");
        when(messageService.getMessage(3)).thenReturn(null);
        ResponseEntity<String> result1 = messageController.deleteMessage(3);
        assertThat(result1.getStatusCodeValue()).isEqualTo(404);

        when(messageService.getMessage(1)).thenReturn(m1);
        when(authManager.getNetId()).thenReturn("user3");
        ResponseEntity<String> result2 = messageController.deleteMessage(1);
        assertThat(result2.getStatusCodeValue()).isEqualTo(401);

        when(authManager.getNetId()).thenReturn("user1");
        ResponseEntity<String> result3 = messageController.deleteMessage(1);
        assertThat(result3.getStatusCodeValue()).isEqualTo(200);
        assertThat(result3.getBody()).isEqualTo("Message successfully deleted!");
        verify(messageService, atMost(1)).deleteMessage(1);

        when(authManager.getNetId()).thenReturn("user2");
        ResponseEntity<String> result4 = messageController.deleteMessage(1);
        assertThat(result4.getStatusCodeValue()).isEqualTo(200);
        assertThat(result4.getBody()).isEqualTo("Message successfully deleted!");
        verify(messageService, atMost(2)).deleteMessage(1);
    }

}