package event.service;

import event.foreigndomain.entitites.Message;
import event.foreigndomain.enums.MessageType;
import event.models.EventJoinModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class MessageService {

    private static final String URL = "http://localhost:8082/api/messages";

    RestTemplate restTemplate = new RestTemplate();

    public MessageService() {}

    public boolean sendJoinMessage(String token, EventJoinModel model, String sender, String recipient) {
        return sendMessage(token, model, sender, recipient, MessageType.JOIN).getStatusCode().value() == 200;
    }

    public boolean sendLeaveMessage(String token, EventJoinModel model, String sender, String recipient) {
        return sendMessage(token, model, sender, recipient, MessageType.LEAVE).getStatusCode().value() == 200;
    }

    public ResponseEntity<String> sendMessage(String token, EventJoinModel model, String sender, String recipient, MessageType type) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token.split(" ")[1]);

        Message message = new Message(model, sender, recipient, type);
        HttpEntity<Message> entity = new HttpEntity<>(message, headers);
        return restTemplate.postForEntity(URL + "/send", entity, String.class);
    }
}
