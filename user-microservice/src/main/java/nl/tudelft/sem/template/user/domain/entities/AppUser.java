package nl.tudelft.sem.template.user.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.user.domain.enums.Gender;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User entity consisting of an ID, gender, username, password, list of boats, list of certificates, list of roles
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long appUserId;
    private Gender gender;

    @Column(unique = true)
    private String username;
    private String password;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<Boat> boatTypePreferences = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<Certificate> certificateCollection = new ArrayList<>();

    public AppUser(String netId, String password) {
        this.username = netId;
        this.password = password;
    }
}


