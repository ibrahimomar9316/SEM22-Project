package event.service;

import event.foreigndomain.entitites.Message;
import event.foreigndomain.enums.MessageType;
import event.models.EventJoinModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;

@Service
@Slf4j
public class MessageService {

    private static final String URL = "http://localhost:8082/api/messages";

    RestTemplate restTemplate = new RestTemplate();

    public MessageService() {}

    /**
     * Sends a join message to the admin of the event that the user wants to join
     *
     * @param token The bearer token for authentication
     * @param model The joinModel which holds the position and eventId
     * @param sender The sender of the message
     * @param recipient The recipient of the message
     * @return An HttpStatus indicating how the sending of the message went
     */
    public HttpStatus sendJoinMessage(String token, EventJoinModel model, String sender, String recipient) throws ConnectException {
        return sendMessage(token, model, sender, recipient, MessageType.JOIN);
    }

    /**
     * Sends a leave message to the admin of the event that the user left
     *
     * @param token The bearer token for authentication
     * @param model The joinModel which holds the position and eventId
     * @param sender The sender of the message
     * @param recipient The recipient of the message
     * @return An HttpStatus indicating how the sending of the message went
     */
    public HttpStatus sendLeaveMessage(String token, EventJoinModel model, String sender, String recipient) throws ConnectException {
        return sendMessage(token, model, sender, recipient, MessageType.LEAVE);
    }

    /**
     * Method for sending messages to the message database.
     *
     * @param token The bearer token for authentication
     * @param model The joinModel which holds the position and eventId
     * @param sender The sender of the message
     * @param recipient The recipient of the message
     * @param type The type of the message specifying what purpose it has
     * @return An HttpStatus indicating how the sending of the message went
     */
    public HttpStatus sendMessage(String token, EventJoinModel model, String sender, String recipient, MessageType type) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token.split(" ")[1]);

        Message message = new Message(model, sender, recipient, type);
        HttpEntity<Message> entity = new HttpEntity<>(message, headers);
        return restTemplate.postForEntity(URL + "/send", entity, String.class).getStatusCode();
    }
}
