package user.authentication;

public interface Validator {

    void setNext(Validator next);

    boolean validate(String token) throws InvalidAuthorizationException;
}
