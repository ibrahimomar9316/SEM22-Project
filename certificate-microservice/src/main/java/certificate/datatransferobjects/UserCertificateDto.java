package certificate.datatransferobjects;

/**
 * Class used to pass data from a microservice to another.
 */
public class UserCertificateDto {
    private String netId;
    private boolean male;
    private boolean competitive;
    private String position;
    private String certificate;

    /**
     * Constructor for the data transfer object used to be passed to certificate microservice
     * to generate the hash for the user.
     *
     * @param netId a string containing the distinctive netId of the user we want to pass along
     * @param male boolean representing if it is a male (true) or a female (false)
     * @param competitive boolean representing if the user is competitive or not
     * @param position string representing the preferred position of the user
     * @param certificate string representing the certificate that the user has
     */
    public UserCertificateDto(String netId, boolean male, boolean competitive, String position, String certificate) {
        this.netId = netId;
        this.male = male;
        this.competitive = competitive;
        this.position = position;
        this.certificate = certificate;
    }

    /**
     * Getter function for the netId.
     *
     * @return a string containing the netId of the corresponding user
     */
    public String getNetId() {
        return netId;
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
