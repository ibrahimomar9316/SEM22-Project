package user.domain.entities;

public class AppUserBuilder implements Builder {
    private transient String netId;

    @Override
    public void setNetId(String netId) {
        this.netId = netId;
    }

    @Override
    public AppUser build() {
        return new AppUser(netId);
    }
}
