package user.controllers;

import java.net.ConnectException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import user.authentication.AuthManager;
import user.domain.entities.Message;
import user.domain.enums.MessageType;
import user.service.EventService;
import user.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    private final EventService eventService;

    private final transient AuthManager auth;

    private static final int OK = 200;

    /**
     * API endpoint for getting all messages in the database.
     *
     * @return The list of all messages stored in the database
     */
    @GetMapping("/all")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok().body(messageService.getAllMessages());
    }

    /**
     * API endpoint for getting the inbox of a user. This is done by finding all messages where the recipient is
     * the user who called the endpoint.
     *
     * @return A list of messages where the recipient is the user who requested their inbox
     */
    @GetMapping({"/inbox"})
    public ResponseEntity<List<Message>> getMessages() {
        return ResponseEntity.ok().body(messageService.getInbox(auth.getNetId()));
    }

    /**
     * API endpoint for sending a message.
     *
     * @param message The message to send.
     * @return HTTP 200 OK status and the message that was sent in the body
     */
    @PostMapping({"/send"})
    public ResponseEntity<String> sendMessage(@RequestBody Message message) {
        messageService.save(message);
        return ResponseEntity.ok().body("Message successfully sent!");
    }

    /**
     * API endpoint for getting all sent message by the user calling the endpoint.
     *
     * @return A list of messages sent by the user calling the endpoint
     */
    @GetMapping({"/pending"})
    public ResponseEntity<List<Message>> getSentMessages() {
        return ResponseEntity.ok().body(messageService.getSentMessages(auth.getNetId()));
    }

    /**
     * API endpoint for accepting join requests.
     *
     * @param token The bearer token for authentication
     * @param messageId The id of the message with the join request that is accepted
     * @return 200 if the accepting went successfully
     *         400 if the message is not a join request
     *         401 if the user wanting to accept a request is not the recipient
     *         404 if the message does not exist
     *         503 if the event server is not reachable
     */
    @PostMapping({"/accept"})
    public ResponseEntity<String> acceptRequest(@RequestHeader("Authorization") String token, @RequestBody long messageId) {
        try {
            Message message = messageService.getMessage(messageId);
            if (message == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (!message.getRecipient().equals(auth.getNetId())) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            if (message.getType() != MessageType.JOIN) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            HttpStatus status = eventService.acceptJoin(token, message);
            if (status.value() != OK) {
                return new ResponseEntity<>(status);
            }

            messageService.deleteMessage(messageId);
            Message ack = new Message(message.getEventId(), MessageType.ACCEPTED,
                    message.getPosition(), message.getRecipient(), message.getSender());
            messageService.save(ack);

            return ResponseEntity.ok().body("Successfully accepted request");
        } catch (ConnectException e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    /**
     * API endpoint for rejecting join requests.
     *
     * @param token The bearer token for authentication
     * @param messageId The id of the message with the join request that is rejected
     * @return 200 if the rejection went successfully
     *         400 if the message is not a join request
     *         401 if the user wanting to reject a request is not the recipient
     *         404 if the message does not exist
     *         503 if the event server is not reachable
     */
    @PostMapping({"/reject"})
    public ResponseEntity<String> denyRequest(@RequestHeader("Authorization") String token, @RequestBody long messageId) {
        Message message = messageService.getMessage(messageId);
        if (message == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!message.getRecipient().equals(auth.getNetId())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (message.getType() != MessageType.JOIN) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        messageService.deleteMessage(messageId);
        Message ack = new Message(message.getEventId(), MessageType.DENIED,
                message.getPosition(), message.getRecipient(), message.getSender());
        messageService.save(ack);

        return ResponseEntity.ok().body("successfully rejected request");
    }

    /**
     * API endpoint for deleting message from the database.
     *
     * @param messageId The id of the message that has to be deleted
     * @return status 401 if the user calling this endpoint is not the author or the recipient of the message
     *         status 404 if the message does not exist in the database
     *         status 200 OK if the message was successfully deleted
     */
    @DeleteMapping({"/delete"})
    public ResponseEntity<String> deleteMessage(@RequestBody long messageId) {
        Message message = messageService.getMessage(messageId);
        if (message == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String netId = auth.getNetId();
        if (!message.getSender().equals(netId) || !message.getRecipient().equals(netId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        messageService.deleteMessage(messageId);
        return ResponseEntity.ok().body("Message successfully deleted!");
    }

}