package nl.tudelft.sem.template.user.domain.entities;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.user.domain.enums.Gender;

/**
 * User entity consisting of an ID, gender, username, password, list of boats, list of certificates, list of roles.
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    private Gender gender;

    @ElementCollection
    private List<LocalDateTime> avDateTimes;
    @Column
    private String boat;

    @Id
    @Column(unique = true)
    private String netId;

    /**
     * Basic constructor for a new AppUser.
     *
     * @param netId a string containing the netId of the user so that we can differentiate them
     *
     */
    public AppUser(String netId) {
        this.netId = netId;


    }


}


