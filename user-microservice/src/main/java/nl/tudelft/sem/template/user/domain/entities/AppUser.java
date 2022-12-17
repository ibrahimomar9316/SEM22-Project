package nl.tudelft.sem.template.user.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.user.domain.enums.Gender;

import java.util.Date;
import java.sql.Time;
/**
 * User entity consisting of an ID, gender, username, password, list of boats, list of certificates, list of roles.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    private Gender gender;

    @Id
    @Column(unique = true)
    private String netId;
    private String boat;
    private Date avdate;
    private Time avtime;

    /**
     * Basic constructor for a new AppUser.
     *
     * @param netId a string containing the netId of the user so that we can differentiate them
     *
     */
    public AppUser(String netId ) {
        this.netId = netId;

    }
/**
 * Function to set User Preferences .
 *
 * @param boat a string containing the preferred boat type of the user
 * @param date a date containing the available date of the user
 * @param time a time containing the available time of the user
 */
    public void setUserPref( String boat, Date date, Time time) {
    this.avdate = date;
    this.avtime = time;
    this.boat = boat;
}
    /**
     * A getter returning the  available time of the user.
     *
     * @return the available time of the user
     */
    public Time getUserAvTime() {
        return avtime;
    }

    /**
     * A getter returning the  available date of the user.
     *
     * @return the available date of the user
     */
    public Date getUserAvDate() {
        return avdate;
    }
    /**
     * A getter returning the  preferred boat type of the user.
     *
     * @return the preferred boat type of the user
     */
    public String getPrefBoat() {
        return boat;
    }


}


