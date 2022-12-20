package user.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model representing an authentication response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthTokenHolder {
    private String token;
}