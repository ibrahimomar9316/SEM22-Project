package nl.tudelft.sem.template.user.domain.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.user.domain.enums.Gender;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
    private String password;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<Boat> boatTypePreferences = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<Certificate> certificateCollection = new ArrayList<>();

    public AppUser(String netId, String password) {
        this.netId = netId;
        this.password = password;
    }
}


