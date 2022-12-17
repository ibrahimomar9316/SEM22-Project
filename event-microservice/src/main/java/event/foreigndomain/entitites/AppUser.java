package event.foreigndomain.entitites;

import event.foreigndomain.enums.Gender;

import java.sql.Time;
import java.util.Date;

/**
 * The type App user.
 */
public class AppUser {

    private transient Gender gender;
    private transient String netId;
    private transient String boat;
    private transient Date avDate;
    private transient Time avTime;


    /**
     * Instantiates a new App user.
     *
     * @param netId  the net id
     * @param gender the gender
     */
    public AppUser(String netId, Gender gender) {
        this.netId = netId;
        this.gender = gender;
    }

    /**
     * Instantiates a new App user.
     *
     * @param netId the net id
     */
    public AppUser(String netId ) {
        this.netId = netId;

    }
    public void UserPref( String boat, Date date, Time time) {
        this.avDate = date;
        this.avTime = time;
        this.boat = boat;
    }

    public String getNetId() {
        return netId;
    }
    public String getBoat() { return boat;}
    public Date getAvDate() {
        return avDate;
    }
    public Time getAvTime() { return avTime;}
}
