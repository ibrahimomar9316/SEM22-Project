package event.foreigndomain.entitites;


import event.domain.enums.Position;
import event.foreigndomain.enums.MessageType;
import event.models.EventJoinModel;

public class Message {


    private long messageId;

    private Long eventId;

    private MessageType type;

    private Position position;

    private String sender;

    private String recipient;


    /**
     * Constructor for a message.
     *
     * @param model A joinModel that holds the eventId and the Position that needs to be joined if applicable
     * @param type The type specifying the goal of the message
     * @param sender The netId of the sender of the message
     * @param recipient The netId of the recipient of the message
     */
    public Message(EventJoinModel model, String sender, String recipient, MessageType type) {
        this.eventId = model.getEventId();
        this.type = type;
        this.position = model.getPosition();
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
