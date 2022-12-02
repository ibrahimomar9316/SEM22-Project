package nl.tudelft.sem.template.user.domain;

import java.util.List;


public class Certificate {
    public List<String> role;
    public String boat;
    public String exp_lvl;

    public Certificate() {
    }

    public Certificate(List<String> role, String boat, String exp_lvl) {
        this.role = role;
        this.boat = boat;
        this.exp_lvl = exp_lvl;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public String getBoat() {
        return boat;
    }

    public void setBoat(String boat) {
        this.boat = boat;
    }

    public String getExp_lvl() {
        return exp_lvl;
    }

    public void setExp_lvl(String exp_lvl) {
        this.exp_lvl = exp_lvl;
    }
}
