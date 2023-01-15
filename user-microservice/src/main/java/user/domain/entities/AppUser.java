package user.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.Hibernate;
import user.domain.enums.Gender;
import user.foreigndomain.enums.Certificate;
import user.foreigndomain.enums.Position;

/**
 * User entity consisting of an ID, gender, username, password, list of boats, list of certificates, list of roles.
 * This data is stored inside a DataBase and also uses the library lombok to autogenerate the getters and constructors.
 */
@Entity
public class AppUser {

    @Id
    @Column(unique = true)
    private String netId;

    @Column
    private Gender gender;

    @Column
    private Position prefPosition;

    @Column
    private boolean competitive;

    @Column
    private Certificate certificate;

    @Column
    private LocalDateTime availableFrom;

    @Column
    private LocalDateTime availableTo;

    /**
     * Basic constructor for a new AppUser.
     */
    public AppUser() {

    }

    /**
     * Basic constructor for a new AppUser.
     *
     * @param netId a string containing the netId of the user so that we can differentiate them
     */
    public AppUser(String netId) {
        this.netId = netId;
    }

    /**
     * Constructor for a new AppUser with specified netId, gender, preferred position, competitive status, certificate,
     * and available time range.
     *
     * @param netId         a string containing the netId of the user
     * @param gender        the gender of the user
     * @param prefPosition  the preferred position of the user
     * @param competitive   whether the user is competitive or not
     * @param certificate   the certificate held by the user
     * @param availableFrom the starting time the user is available
     * @param availableTo   the ending time the user is available
     */
    public AppUser(String netId, Gender gender, Position prefPosition, boolean competitive, Certificate certificate,
                   LocalDateTime availableFrom, LocalDateTime availableTo) {
        this.netId = netId;
        this.gender = gender;
        this.prefPosition = prefPosition;
        this.competitive = competitive;
        this.certificate = certificate;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
    }

    /**
     * Returns the netId of the user.
     *
     * @return the netId of the user
     */
    public String getNetId() {
        return netId;
    }

    /**
     * Sets the netId of the user.
     *
     * @param netId the new netId of the user
     */
    public void setNetId(String netId) {
        this.netId = netId;
    }

    /**
     * Returns the gender of the user.
     *
     * @return the gender of the user
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Returns the preferred position of the user.
     *
     * @return the preferred position of the user
     */
    public Position getPrefPosition() {
        return prefPosition;
    }

    /**
     * Returns whether the user is competitive or not.
     *
     * @return true if the user is competitive, false otherwise
     */
    public boolean isCompetitive() {
        return competitive;
    }

    /**
     * Returns the certificate held by the user.
     *
     * @return the certificate held by the user
     */
    public Certificate getCertificate() {
        return certificate;
    }

    /**
     * Returns the starting time the user is available.
     *
     * @return the starting time the user is available
     */
    public LocalDateTime getAvailableFrom() {
        return availableFrom;
    }

    /**
     * Returns the ending time the user is available.
     *
     * @return the ending time the user is available
     */
    public LocalDateTime getAvailableTo() {
        return availableTo;
    }

    /**
     * Updates the user's information, including gender, preferred position, competitive status, certificate,
     * and available time range.
     *
     * @param gender        the new gender of the user
     * @param prefPosition  the new preferred position of the user
     * @param competitive   the new competitive status of the user
     * @param certificate   the new certificate held by the user
     * @param availableFrom the new starting time the user is available
     * @param availableTo   the new ending time the user is available
     */
    public void upDate(Gender gender,
                       Position prefPosition,
                       boolean competitive,
                       Certificate certificate,
                       LocalDateTime availableFrom,
                       LocalDateTime availableTo) {
        this.gender = gender;
        this.prefPosition = prefPosition;
        this.competitive = competitive;
        this.certificate = certificate;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
    }

    /**
     * Compares this user to the specified object.
     * The result is true if and only if the argument is not null and is an AppUser object that contains
     * the same netId as this object.
     *
     * @param o the object to compare this AppUser against
     * @return true if the given object represents an AppUser with the same netId, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        AppUser appUser = (AppUser) o;
        return netId != null && Objects.equals(netId, appUser.netId);
    }

    /**
     * Returns a hash code value for the user.
     *
     * @return the hash code value for this AppUser
     */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    /**
     * Returns a string representation of the user.
     *
     * @return a string representation of this AppUser, including the netId, gender, preferred position, competitive
     * status, certificate, and available time range
     */
    @Override
    public String toString() {
        return "AppUser{"
                + "netId='" + netId + '\''
                + ", gender=" + gender
                + ", prefPosition=" + prefPosition
                + ", competitive=" + competitive
                + ", certificate=" + certificate
                + ", availableFrom=" + availableFrom
                + ", availableTo=" + availableTo
                + '}';
    }
}


