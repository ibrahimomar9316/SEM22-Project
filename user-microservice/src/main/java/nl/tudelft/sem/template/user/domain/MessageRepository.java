package nl.tudelft.sem.template.user.domain;

import java.util.List;
import nl.tudelft.sem.template.user.domain.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository for the messages.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT m FROM Message m WHERE m.recipient = ?1")
    List<Message> getMessagesByRecipient(String netId);
}
