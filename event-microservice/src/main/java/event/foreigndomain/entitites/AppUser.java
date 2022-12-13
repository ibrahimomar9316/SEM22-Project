package event.foreigndomain.entitites;

import event.foreigndomain.enums.Gender;

/**
 * The type App user.
 */
public class AppUser {

    private transient Gender gender;
    private transient String netId;

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
    public AppUser(String netId) {
        this.netId = netId;
    }

    public String getNetId() {
        return netId;
    }
}
