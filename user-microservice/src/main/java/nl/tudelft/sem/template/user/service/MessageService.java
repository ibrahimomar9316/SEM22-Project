package nl.tudelft.sem.template.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.tudelft.sem.template.user.authentication.AuthManager;
import nl.tudelft.sem.template.user.domain.MessageRepository;
import nl.tudelft.sem.template.user.domain.entities.Message;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MessageService {

    private final MessageRepository repo;

    private final transient AuthManager auth;

    /**
     * Getter for all messages in the database.
     *
     * @return A list of all messages in the database
     */
    public List<Message> getAllMessages() {
        return repo.findAll();
    }

    /**
     * Getter for the inbox of a user.
     *
     * @param netId The netId of the user
     * @return A list of messages where the user is the recipient
     */
    public List<Message> getInbox(String netId) {
        return repo.getMessagesByRecipient(netId);
    }

    /**
     * Saves the given messages to the database.
     *
     * @param message The message to save
     * @return The message that was saved
     */
    public Message save(Message message) {
        return this.repo.save(message);
    }

}
