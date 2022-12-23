package authentication.domain.user;

public class AppUserBuilder implements Builder {
    private transient NetId netId;
    private transient HashedPassword password;

    @Override
    public void setNetId(NetId netId) {
        this.netId = netId;
    }

    @Override
    public void setPassword(HashedPassword password) {
        this.password = password;
    }

    @Override
    public AppUser build() {
        return new AppUser(netId, password);
    }
}
