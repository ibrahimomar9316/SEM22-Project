package user.datatransferobjects;

import lombok.Data;
import user.domain.enums.Gender;

/**
 * Class used for data transfer, being a dataTransferObject.
 * It is used to pass data from user-microservice to certificate-microservice, passing data that we want to hash.
 */
@Data
public class UserCertificateDto {
    private transient Gender gender;
    private transient boolean competitive;
    private transient String position;
    private transient String certificate;


    /**
     * Constructor for the DTO used to send information to certificate-microservie
     * to generate the hash (for the preferences) for a specific user.
     *
     * @param gender enum representing if it is a male (true) or a female (false)
     * @param competitive boolean representing if the user is competitive or not
     * @param position string representing the preferred position of the user
     * @param certificate string representing the certificate that the user has
     */
    public UserCertificateDto(Gender gender, boolean competitive, String position, String certificate) {
        this.gender = gender;
        this.competitive = competitive;
        this.position = position;
        this.certificate = certificate;
    }

    /**
     * Getter function for the gender of the corresponding user.
     *
     * @return a boolean representing the gender of the corresponding user
     */
    public Gender getGender() {
        return gender;
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
