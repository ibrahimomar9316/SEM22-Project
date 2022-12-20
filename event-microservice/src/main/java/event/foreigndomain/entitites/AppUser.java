package event.foreigndomain.entitites;

import event.foreigndomain.enums.Certificate;
import event.foreigndomain.enums.Gender;
import event.foreigndomain.enums.Position;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * User entity consisting of an ID, gender, username, password, list of boats, list of certificates, list of roles.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    /**
     * Basic constructor for a new AppUser.
     *
     * @param netId a string containing the netId of the user so that we can differentiate them
     */
    public AppUser(String netId) {
        this.netId = netId;
    }
}


