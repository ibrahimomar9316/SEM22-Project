package user.domain.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import user.domain.enums.Gender;
import user.domain.objects.AvDates;
import user.foreigndomain.enums.Certificate;
import user.foreigndomain.enums.Position;

/**
 * User entity consisting of an ID, gender, username, password, list of boats, list of certificates, list of roles.
 * This data is stored inside a DataBase and also uses the library lombok to autogenerate the getters and constructors.
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

    @Column
    private LocalDateTime availableFrom;

    @Column
    private LocalDateTime availableTo;

    /**
     * Basic constructor for a new AppUser.
     *
     * @param netId a string containing the netId of the user so that we can differentiate them
     */
    public AppUser(String netId) {
        this.netId = netId;
        this.avDatesList = new ArrayList<>();
    }

}


