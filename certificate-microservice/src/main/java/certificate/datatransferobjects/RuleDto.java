package certificate.datatransferobjects;

import lombok.Data;

/**
 * Class used to pass data from a microservice to another.
 */
@Data
public class RuleDto {
    private transient long eventId;
    private transient boolean gendered;
    private transient boolean pro;
    private transient String certificate;


    /**
     * Instantiates a new Rule dto.
     */
    public RuleDto() {

    }

    /**
     * Instantiates a new Rule dto.
     *
     * @param eventId     the event id
     * @param gendered    the gendered
     * @param pro         the pro
     * @param certificate the certificate
     */
    public RuleDto(long eventId, boolean gendered, boolean pro, String certificate) {
        this.eventId = eventId;
        this.gendered = gendered;
        this.pro = pro;
        this.certificate = certificate;
    }

    /**
     * Gets certificate.
     *
     * @return the certificate
     */
    public String getCertificate() {
        return certificate;
    }

    /**
     * Gets event id.
     *
     * @return the event id
     */
    public long getEventId() {
        return eventId;
    }

    /**
     * Sets certificate.
     *
     * @param certificate the certificate
     */
    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    /**
     * Sets gendered.
     *
     * @param gendered the gendered
     */
    public void setGendered(boolean gendered) {
        this.gendered = gendered;
    }

    /**
     * Sets pro.
     *
     * @param pro the pro
     */
    public void setPro(boolean pro) {
        this.pro = pro;
    }
}
