package authentication.models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Model representing an authentication request.
 */
@Data
@AllArgsConstructor
public class AuthenticationRequestModel {
    private String netId;
    private String password;
}