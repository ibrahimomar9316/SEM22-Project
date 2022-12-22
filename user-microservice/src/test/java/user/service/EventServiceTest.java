package user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.net.ConnectException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import user.domain.entities.Message;
import user.domain.enums.MessageType;
import user.foreigndomain.enums.Position;

class EventServiceTest {

    @Test
    void acceptJoin() throws ConnectException {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        EventService eventService = new EventService();
        eventService.setRestTemplate(restTemplate);
        when(restTemplate.postForEntity(anyString(), any(), any())).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        String token = "mew mew";
        Message m = new Message(1, 2L, MessageType.LEAVE, Position.COACH, "user1", "user2");
        assertThat(eventService.acceptJoin(token, m).value()).isEqualTo(200);
    }
}