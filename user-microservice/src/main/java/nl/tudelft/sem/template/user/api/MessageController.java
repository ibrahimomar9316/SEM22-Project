package nl.tudelft.sem.template.user.api;

import lombok.RequiredArgsConstructor;
import nl.tudelft.sem.template.user.domain.entities.Message;
import nl.tudelft.sem.template.user.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

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
     * the user who asks for their inbox.
     *
     * @param netId The netId of the user who wants their inbox
     * @return A list of messages where the recipient is the user who requested their inbox
     */
    @GetMapping({"/inbox"})
    public ResponseEntity<List<Message>> getMessages(@RequestBody String netId) {
        return ResponseEntity.ok().body(messageService.getInbox(netId));
    }

    /**
     * API endpoint for sending a message.
     *
     * @param message The message to send.
     * @return HTTP 200 OK status and the message that was sent in the body
     */
    @PostMapping({"/send"})
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        return ResponseEntity.ok().body(messageService.save(message));
    }

}

