package nl.tudelft.sem.template.authentication.foreignDomain;

import nl.tudelft.sem.template.authentication.domain.user.Role;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class UserAppUser {

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

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<Role> roles = new ArrayList<>();

    public UserAppUser() {
    }

    public UserAppUser(String netId, String password, List<Role> roles) {
        this.username = netId;
        this.password = password;
        this.roles = roles;
    }

    public UserAppUser(Long appUserId, Gender gender, String username, String password, List<Boat> boatTypePreferences, List<Certificate> certificateCollection, List<Role> roles) {
        this.appUserId = appUserId;
        this.gender = gender;
        this.username = username;
        this.password = password;
        this.boatTypePreferences = boatTypePreferences;
        this.certificateCollection = certificateCollection;
        this.roles = roles;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Boat> getBoatTypePreferences() {
        return boatTypePreferences;
    }

    public void setBoatTypePreferences(List<Boat> boatTypePreferences) {
        this.boatTypePreferences = boatTypePreferences;
    }

    public List<Certificate> getCertificateCollection() {
        return certificateCollection;
    }

    public void setCertificateCollection(List<Certificate> certificateCollection) {
        this.certificateCollection = certificateCollection;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
