package authentication.datatransferobjects;

/**
 * Class used for data transfer, being a dataTransferObject.
 * It is used to pass data from authentication-microservice to user-microservice, passing data that we want to store.
 */
public class UserDto {

    private String username;

    /**
     * Constructor used to create the DTO.
     *
     * @param username a string representing the username/netId of the user we want to store
     */
    public UserDto(String username) {
        this.username = username;
    }

    /**
     * Getter function for the username/netId String value.
     *
     * @return a string representing the username/netId of the user we want to pass along microservices
     */
    public String getUsername() {
        return username;
    }
}
