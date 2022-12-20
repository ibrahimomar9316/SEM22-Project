package certificate.datatransferobjects;

import lombok.Data;

/**
 * Class used to pass data from a microservice to another.
 */
@Data
public class UserCertificateDto {
    private transient boolean male;
    private transient boolean competitive;
    private transient String position;
    private transient String certificate;

    /**
     * Constructor for the data transfer object used to be passed to certificate microservice
     * to generate the hash for the user.
     *
     * @param male boolean representing if it is a male (true) or a female (false)
     * @param competitive boolean representing if the user is competitive or not
     * @param position string representing the preferred position of the user
     * @param certificate string representing the certificate that the user has
     */
    public UserCertificateDto(boolean male, boolean competitive, String position, String certificate) {
        this.male = male;
        this.competitive = competitive;
        this.position = position;
        this.certificate = certificate;
    }

    /**
     * Getter function for the gender of the corresponding user.
     *
     * @return a boolean representing the gender of the corresponding user
     */
    public boolean isMale() {
        return male;
    }

    /**
     * Getter function for user if it is competitive or not.
     *
     * @return a boolean representing if the corresponding user is competitive or not
     */
    public boolean isCompetitive() {
        return competitive;
    }

    /**
     * Getter function for the preferred position of the corresponding user.
     *
     * @return a string representing the preferred position of the corresponding user
     */
    public String getPosition() {
        return position;
    }

    /**
     * Getter function for the certificate owned by the corresponding user.
     *
     * @return a string representing the certificate owned by the corresponding user
     */
    public String getCertificate() {
        return certificate;
    }
}
