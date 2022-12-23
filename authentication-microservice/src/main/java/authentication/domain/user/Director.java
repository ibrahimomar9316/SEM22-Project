package authentication.domain.user;

public class Director {
    public void createAppUser(Builder builder, NetId netId, HashedPassword password) {
        builder.setNetId(netId);
        builder.setPassword(password);
    }
}

