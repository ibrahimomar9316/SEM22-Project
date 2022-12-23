package event.service;

import event.foreigndomain.entitites.Message;
import event.foreigndomain.enums.MessageType;
import event.models.EventJoinModel;
import java.net.ConnectException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * The type Message service.
 */
@Service
@Slf4j
public class MessageService {

    private static final String URL = "http://localhost:8082/api/messages";

    private static RestTemplate restTemplate = new RestTemplate();

    /**
     * Gets rest template.
     *
     * @return the rest template
     */
    public static RestTemplate getRestTemplate() {
        return restTemplate;
    }

    /**
     * Sets rest template.
     *
     * @param restTemplate the rest template
     */
    public static void setRestTemplate(RestTemplate restTemplate) {
        MessageService.restTemplate = restTemplate;
    }

    /**
     * Instantiates a new Message service.
     */
    public MessageService() {}

    /**
     * Sends a join message to the admin of the event that the user wants to join.
     *
     * @param token     The bearer token for authentication
     * @param model     The joinModel which holds the position and eventId
     * @param sender    The sender of the message
     * @param recipient The recipient of the message
     * @return An HttpStatus indicating how the sending of the message went
     * @throws ConnectException the connect exception
     */
    public HttpStatus sendJoinMessage(String token, EventJoinModel model, String sender, String recipient)
            throws ConnectException {
        return sendMessage(token, model, sender, recipient, MessageType.JOIN);
    }

    /**
     * Sends a leave message to the admin of the event that the user left.
     *
     * @param token     The bearer token for authentication
     * @param model     The joinModel which holds the position and eventId
     * @param sender    The sender of the message
     * @param recipient The recipient of the message
     * @return An HttpStatus indicating how the sending of the message went
     * @throws ConnectException the connect exception
     */
    public HttpStatus sendLeaveMessage(String token, EventJoinModel model, String sender, String recipient)
            throws ConnectException {
        return sendMessage(token, model, sender, recipient, MessageType.LEAVE);
    }

    /**
     * Method for sending messages to the message database.
     *
     * @param token     The bearer token for authentication
     * @param model     The joinModel which holds the position and eventId
     * @param sender    The sender of the message
     * @param recipient The recipient of the message
     * @param type      The type of the message specifying what purpose it has
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
