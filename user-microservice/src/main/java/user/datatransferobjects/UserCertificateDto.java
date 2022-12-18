package user.datatransferobjects;

public class UserCertificateDto {
    private String netId;
    private boolean male;
    private boolean competitive;
    private String position;
    private String certificate;

    public UserCertificateDto(String netId, boolean male, boolean competitive, String position, String certificate) {
        this.netId = netId;
        this.male = male;
        this.competitive = competitive;
        this.position = position;
        this.certificate = certificate;
    }

    public String getNetId() {
        return netId;
    }

    public boolean isMale() {
        return male;
    }

    public boolean isCompetitive() {
        return competitive;
    }

    public String getPosition() {
        return position;
    }

    public String getCertificate() {
        return certificate;
    }
}
