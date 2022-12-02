package nl.tudelft.sem.template.user.domain;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.AUTO;

@Entity @Data
public class AppUser {
    @Id @GeneratedValue(strategy = AUTO)
    private Long id;
    private String name;
    private Character gender;
 //   private List<LocalDateTime> availability;
    private String boatPreference;
//    @ManyToMany(fetch=EAGER)
//    private Collections<Certificate> eventPreferences;

    public AppUser() {
    }

    public AppUser(String name, Character gender) {
        this.name = name;
        this.gender = gender;
       // this.availability = new ArrayList<>();
        this.boatPreference = "";
        //this.eventPreferences = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

}


