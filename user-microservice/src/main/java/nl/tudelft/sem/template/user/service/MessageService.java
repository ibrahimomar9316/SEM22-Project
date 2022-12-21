package nl.tudelft.sem.template.user.service;

import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.tudelft.sem.template.user.authentication.AuthManager;
import nl.tudelft.sem.template.user.domain.MessageRepository;
import nl.tudelft.sem.template.user.domain.entities.Message;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MessageService {

    private final MessageRepository repo;

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
        return this.repo.saveAndFlush(message);
    }

    /**
     * Getter for all the messages sent by a user.
     *
     * @param netId The netId of the user
     * @return A list of messages sent by the user
     */
    public List<Message> getSentMessages(String netId) {
        return repo.getMessagesBySender(netId);
    }

    /**
     * Gets a message from the database with the given id.
     *
     * @param messageId The id of the message to get from the repository
     * @return null if not found, else the message that was found
     */
    public Message getMessage(long messageId) {
        if (!repo.existsById(messageId)) {
            return null;
        }
        return repo.findById(messageId).get();
    }

    /**
     * Deletes the message with the given id from the database.
     *
     * @param messageId The id of the message to delete
     */
    public void deleteMessage(long messageId) {
        this.repo.deleteById(messageId);
    }

}
