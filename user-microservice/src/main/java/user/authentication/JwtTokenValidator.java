package user.authentication;

import io.jsonwebtoken.JwtException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

public class JwtTokenValidator extends DefaultValidator {

    private transient JwtTokenVerifier jwtTokenVerifier;

    private transient HttpServletRequest request;

    public JwtTokenValidator(JwtTokenVerifier jwtTokenVerifier) {
        super();
        this.jwtTokenVerifier = jwtTokenVerifier;
    }

    /**
     * Validates the token by checking if the token itself is valid.
     *
     * @param token the token to check
     * @return The result of the next validator in the chain
     * @throws InvalidAuthorizationException When the token is invalid
     */
    @Override
    public boolean validate(String token) throws InvalidAuthorizationException {
        if (jwtTokenVerifier.validateToken(token)) {
            String netId = jwtTokenVerifier.getNetIdFromToken(token);
            var authenticationToken = new UsernamePasswordAuthenticationToken(
                    netId,
                    null, List.of() // no credentials and no authorities
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(request));

            // After setting the Authentication in the context, we specify
            // that the current user is authenticated. So it passes the
            // Spring Security Configurations successfully.
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            return super.validate(token);
        }
        throw new JwtException("Unable to verify token");
    }

    /**
     * Setter for the request to authorize.
     *
     * @param request The request to set
     */
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
