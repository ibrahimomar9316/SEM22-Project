package event.foreignDomain.entitites;

import event.foreignDomain.enums.Gender;

public class AppUser {

    private Gender gender;

    private String netId;

    public AppUser(String netId, Gender gender) {
        this.netId = netId;
        this.gender = gender;
    }
}
