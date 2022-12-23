package user.domain.entities;

public class Director {
    public void createAppUser(Builder builder, String netId) {
        builder.setNetId(netId);
    }
}
