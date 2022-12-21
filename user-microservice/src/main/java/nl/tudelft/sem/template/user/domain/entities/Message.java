package nl.tudelft.sem.template.user.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.user.domain.enums.MessageType;
import nl.tudelft.sem.template.user.foreigndomain.enums.Position;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @Column(name = "messageId", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long messageId;

    @Column
    private Long eventId;

    @Column
    private MessageType type;

    @Column
    private Position position;

    @Column
    private String sender;

    @Column
    private String recipient;


    /**
     * Constructor for a message.
     *
     * @param eventId The id of the event, the message is used for to communicate
     * @param type The type specifying the goal of the message
     * @param position The position in the event which is accepted, denied, left, joined etc.
     * @param sender The netId of the sender of the message
     * @param recipient The netId of the recipient of the message
     */
    public Message(Long eventId, MessageType type, Position position, String sender, String recipient) {
        this.eventId = eventId;
        this.type = type;
        this.position = position;
        this.sender = sender;
        this.recipient = recipient;
    }

    /**
     * getter for the messageId.
     *
     * @return The messageId of the message
     */
    public long getMessageId() {
        return messageId;
    }

    /**
     * Setter for the messageId.
     *
     * @param messageId the messageId to set
     */
    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    /**
     * getter for the eventId.
     *
     * @return The eventId of the message
     */
    public Long getEventId() {
        return eventId;
    }

    /**
     * Setter for the eventId.
     *
     * @param eventId the eventId to set
     */
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    /**
     * getter for the type of the message.
     *
     * @return The type of the message
     */
    public MessageType getType() {
        return type;
    }

    /**
     * Setter for the type.
     *
     * @param type the type to set
     */
    public void setType(MessageType type) {
        this.type = type;
    }

    /**
     * getter for the Position in the message.
     *
     * @return The Position in the message
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Setter for the Position.
     *
     * @param position the Position to set
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * getter for the netId of the sender of the message.
     *
     * @return The netId of the sender of the message
     */
    public String getSender() {
        return sender;
    }

    /**
     * Setter for the netId of the sender.
     *
     * @param sender the netId of the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * getter for the netId of the recipient of the message.
     *
     * @return The messageId of the recipient of the message
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Setter for the netId of the recipient.
     *
     * @param recipient the netId of the recipient to set
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
