package event.datatransferobjects;

import event.foreigndomain.enums.Gender;
import lombok.Data;

/**
 * Class used for data transfer, being a dataTransferObject.
 * It is used to pass data from event-microservice to certificate-microservice, passing data that we want to hash.
 */
@Data
public class RuleDto {
    private transient long eventId;
    private transient Gender genderConstraint;
    private transient boolean pro;
    private transient String certificate;



    /**
     * Instantiates a new Rule dataTransferObject.
     *
     * @param eventId     long value representing the ID of the event we are hashing the rules for
     * @param gendered    enum representing the gender restrictions
     * @param pro         boolean representing if the event requires only professionals or not
     * @param certificate string representing the minimum certificates required to participate in the event
     */
    public RuleDto(long eventId, Gender gendered, boolean pro, String certificate) {
        this.eventId = eventId;
        this.genderConstraint = gendered;
        this.pro = pro;
        this.certificate = certificate;
    }


    /**
     * Getter function used to retrieve the certificate from the DTO.
     *
     * @return A string representing the certificate requirements stored in the DTO
     */
    public String getCertificate() {
        return certificate;
    }

    /**
     * Getter function used to retrieve the id of an event from the DTO.
     *
     * @return A long value representing the id of the event stored in the DTO
     */
    public long getEventId() {
        return eventId;
    }

    /**
     * Setter function used to set the certificate from the DTO.
     *
     * @param certificate the certificate we want to store in the DTO
     */
    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    /**
     * Setter function used to set the certificate from the DTO.
     *
     * @param genderConstraint the certificate we want to store in the DTO
     */
    public void setGenderConstraint(Gender genderConstraint) {
        this.genderConstraint = genderConstraint;
    }


    /**
     * Setter function used to set the rpo from the DTO.
     *
     * @param pro the pro we want to store in the DTO
     */
    public void setPro(boolean pro) {
        this.pro = pro;
    }
}
