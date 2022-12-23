package user.authentication;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Request filter for JWT security.
 * <p>
 * The request filter is called once for each request and makes it possible to modify the request
 * before it reaches the application. If an authorization header is present in the request,
 * the filter will validate it and authenticate the token.
 * </p>
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String WWW_AUTHENTICATE_HEADER = "WWW-Authenticate";
    public static final String AUTHORIZATION_AUTH_SCHEME = "Bearer";

    private transient Validator validator;

    private transient JwtTokenValidator jwtTokenValidator;

    /**
     * Constructor for the requestFilter used for authorization.
     *
     * @param jwtTokenVerifier The jwtTokenVerifier to use for verifying
     */
    @Autowired
    public JwtRequestFilter(JwtTokenVerifier jwtTokenVerifier) {
        this.validator = new HeaderValidator();
        this.jwtTokenValidator = new JwtTokenValidator(jwtTokenVerifier);
        this.validator.setNext(jwtTokenValidator);
    }

    /**
     * This filter will verify and authenticate a JWT token if a valid authorization header is set.
     *
     * @param request     The current request we are handling.
     * @param response    The current response we are building.
     * @param filterChain The next link in the filter chain.
     * @throws ServletException Exception.
     * @throws IOException      Exception
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Set the request for the token validator
        jwtTokenValidator.setRequest(request);

        // Get authorization header
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        try {
            validator.validate(authorizationHeader);
        } catch (ExpiredJwtException e) {
            System.err.println("JWT token has expired.");
        } catch (IllegalArgumentException | JwtException e) {
            System.err.println("Unable to parse JWT token");
        } catch (InvalidAuthorizationException e) {
            System.err.println("Invalid authorization header");
        }

        filterChain.doFilter(request, response);
    }
}