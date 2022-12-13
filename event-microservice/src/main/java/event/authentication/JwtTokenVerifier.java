package event.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Verifies the JWT token in the request for validity.
 */
@Component
public class JwtTokenVerifier {
    @Value("${jwt.secret}")  // automatically loads jwt.secret from resources/application.properties
    private transient String jwtSecret;

    /**
     * Validate the JWT token for expiration.
     */
    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    /**
     * Method to get the NedId from a token.
     *
     * @param token the token we want the netId from
     * @return the NedId of the user's token
     */
    public String getNetIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Method to get the expiration date from a token.
     *
     * @param token the token we want the expiration date from
     * @return the expiration date of the given token
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Method that checks if a token has expired.
     *
     * @param token the token we want to check if it's expired
     * @return a boolean representing if the token is expired (ture) or not (false)
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Method to get the converted claims from a specific token.
     *
     * @param token the token we want to get the claim from
     * @param claimsResolver resolver function that converts the claims
     * @return the processed claim used for extraction of data
     */
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Method to extract the claims from a token.
     *
     * @param token the token we want to extract the claims from
     * @return the claims we want to extract
     */
    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }
}
