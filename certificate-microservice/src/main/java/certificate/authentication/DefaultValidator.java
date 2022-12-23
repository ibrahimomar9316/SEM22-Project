package certificate.authentication;

public class DefaultValidator implements Validator {

    private transient Validator next;

    /**
     * Setter for the next validator in the chain.
     *
     * @param next The next validator that has to be set
     */
    @Override
    public void setNext(Validator next) {
        this.next = next;
    }

    /**
     * Checks if the given token is valid.
     *
     * @param token the token to check
     * @return A boolean indicating if the token is valid or not
     * @throws InvalidAuthorizationException When the authorization header is invalid
     */
    @Override
    public boolean validate(String token) throws InvalidAuthorizationException {
        if (this.next == null) {
            return true;
        }
        return next.validate(token);
    }
}
