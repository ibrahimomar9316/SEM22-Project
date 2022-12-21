package nl.tudelft.sem.template.user.service;

import java.net.ConnectException;
import nl.tudelft.sem.template.user.domain.entities.Message;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EventService {

    private static final String URL = "http://localhost:8083/api/event";

    private static RestTemplate restTemplate = new RestTemplate();

    public EventService() {}

    /**
     * Accepts the join request of a user by requesting the event server
     * to add the user to the event. Also, a confirmation is sent.
     *
     * @param token The bearer token to authenticate
     * @param message The message to send to the event server for joining an event
     * @return The statusCode from the event server after the request
     * @throws ConnectException When connecting to the event server is not possible
     */
    public HttpStatus acceptJoin(String token, Message message) throws ConnectException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token.split(" ")[1]);

        HttpEntity<Message> entity = new HttpEntity<>(message, headers);
        ResponseEntity<String> res = restTemplate.postForEntity(URL + "/add", entity, String.class);
        return res.getStatusCode();
    }
}
