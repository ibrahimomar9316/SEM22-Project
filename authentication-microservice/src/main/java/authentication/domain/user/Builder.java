package authentication.domain.user;

public interface Builder {
    public void setNetId(NetId netId);

    public void setPassword(HashedPassword password);

    public AppUser build();
}
