package authentication.models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Model representing a registration request.
 */
@Data
@AllArgsConstructor
public class RegistrationRequestModel {
    private String netId;
    private String password;
}