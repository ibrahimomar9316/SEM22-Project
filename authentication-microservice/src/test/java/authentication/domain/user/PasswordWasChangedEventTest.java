package authentication.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PasswordWasChangedEventTest {

    @Test
    void getUser() {
        AppUser user = new AppUser(new NetId("user"), new HashedPassword("dqhuydgq"));
        PasswordWasChangedEvent event = new PasswordWasChangedEvent(user);
        assertEquals(event.getUser(), user);
    }
}