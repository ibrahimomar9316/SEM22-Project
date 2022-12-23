package authentication.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;


class AppUserTest {

    @Test
    void changePassword() {
        AppUser user = new AppUser(new NetId("id"), new HashedPassword("hash"));
        assertThat(user.getPassword().toString()).isEqualTo("hash");

        user.changePassword(new HashedPassword("diff"));
        assertThat(user.getPassword().toString()).isEqualTo("diff");
    }

    @Test
    void testEquals() {
        AppUser user1 = new AppUser(new NetId("id"), new HashedPassword("hash"));
        AppUser user2 = new AppUser(new NetId("id"), new HashedPassword("hash"));
        AppUser user3 = new AppUser(new NetId("nope"), new HashedPassword("diff"));
        user3.setId(2);

        assertThat(user1).isEqualTo(user1);
        assertThat(user1).isEqualTo(user2);

        assertThat(user1).isNotEqualTo(user3);
        assertThat(user1).isNotEqualTo(null);
        assertThat(user1).isNotEqualTo(1);
    }

    @Test
    void testHashCode() {
        AppUser user1 = new AppUser(new NetId("id"), new HashedPassword("hash"));
        AppUser user2 = new AppUser(new NetId("id"), new HashedPassword("hash"));

        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());
    }
}