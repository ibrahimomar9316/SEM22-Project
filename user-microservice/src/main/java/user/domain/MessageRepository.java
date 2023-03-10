package user.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import user.domain.entities.Message;

/**
 * Repository for the messages.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT m FROM Message m WHERE m.recipient = ?1")
    List<Message> getMessagesByRecipient(String netId);

    @Query(value = "SELECT m FROM Message m WHERE m.sender = ?1")
    List<Message> getMessagesBySender(String netId);
}
