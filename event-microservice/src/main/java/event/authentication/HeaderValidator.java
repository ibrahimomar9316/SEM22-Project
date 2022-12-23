package event.authentication;

public class HeaderValidator extends DefaultValidator {

    public static final String AUTHORIZATION_AUTH_SCHEME = "Bearer";

    /**\
     * Validates the token by checking if the authorization header is valid.
     *
     * @param token the token to check
     * @return The boolean result of the rest of the chain
     * @throws InvalidAuthorizationException If the header of the token is invalid
     */
    @Override
    public boolean validate(String token) throws InvalidAuthorizationException {
        if (token != null) {
            String[] directives = token.split(" ");
            if (directives.length == 2 && directives[0].equals(AUTHORIZATION_AUTH_SCHEME)) {
                token = directives[1];
                return super.validate(token);
            }
        }
        throw new InvalidAuthorizationException();
    }

}
