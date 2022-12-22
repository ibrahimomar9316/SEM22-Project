package user.domain.entities;

import user.domain.enums.Gender;

public interface Builder {
    void setNetId(String netId);

    AppUser build();
}
