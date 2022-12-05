package commons;

import java.util.Objects;

public class BoatMembers {

    //TODO: change from String data type to user
    private String cox;
    private String coach;
    private String portSideRower;
    private String starboardRower;
    private String scullingRower;

    /**
     * Constructor for boat members class.
     *
     * @param cox The cox of the boat
     * @param coach The coach of the boat
     * @param portSideRower The Port Side Rower of the boat
     * @param sarboardRower The Sarboard Rower of the boat
     * @param scullingRower The Sculling Rower of the boat
     */
    public BoatMembers(String cox, String coach, String portSideRower, String sarboardRower, String scullingRower) {
        this.cox = cox;
        this.coach = coach;
        this.portSideRower = portSideRower;
        this.starboardRower = sarboardRower;
        this.scullingRower = scullingRower;
    }

    /**
     * Getter for the cox of the boat.
     *
     * @return the cox of the boat
     */
    public String getCox() {
        return cox;
    }

    /**
     * Setter for the cox of the boat.
     *
     * @param cox the cox that we want to assign
     */
    public void setCox(String cox) {
        this.cox = cox;
    }

    /**
     * Getter for the coach of the boat.
     *
     * @return the coach of the boat
     */
    public String getCoach() {
        return coach;
    }

    /**
     * Setter for the coach of the boat.
     *
     * @param coach the coach that we want to assign
     */
    public void setCoach(String coach) {
        this.coach = coach;
    }

    /**
     * Getter for the port side rower of the boat.
     *
     * @return the port side rower of the boat
     */
    public String getPortSideRower() {
        return portSideRower;
    }

    /**
     * Setter for the port side rower of the boat.
     *
     * @param portSideRower the port side rower that we want to assign
     */
    public void setPortSideRower(String portSideRower) {
        this.portSideRower = portSideRower;
    }

    /**
     * Getter for the starboard rower of the boat.
     *
     * @return the starboard rower of the boat
     */
    public String getStarboardRower() {
        return starboardRower;
    }

    /**
     * Setter for the starboard rower of the boat.
     *
     * @param starboardRower the starboard rower that we want to assign
     */
    public void setStarboardRower(String starboardRower) {
        this.starboardRower = starboardRower;
    }

    /**
     * Getter for the sculling rower of the boat.
     *
     * @return the sculling rower of the boat
     */
    public String getScullingRower() {
        return scullingRower;
    }

    /**
     * Setter for the sculling rower of the boat.
     *
     * @param scullingRower the sculling rower that we want to assign
     */
    public void setScullingRower(String scullingRower) {
        this.scullingRower = scullingRower;
    }

    /**
     * Method to check equality between two boats' members.
     *
     * @param o the boat we want to compare with
     * @return a boolean depending on the equality
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BoatMembers)) {
            return false;
        }
        BoatMembers that = (BoatMembers) o;
        return Objects.equals(cox, that.cox)
            && Objects.equals(coach, that.coach)
            && Objects.equals(portSideRower, that.portSideRower)
            && Objects.equals(starboardRower, that.starboardRower)
            && Objects.equals(scullingRower, that.scullingRower);
    }

    /**
     * Basic hash generator for a boat.
     *
     * @return the int hash of the boat
     */
    @Override
    public int hashCode() {
        return Objects.hash(cox, coach, portSideRower, starboardRower, scullingRower);
    }
}
