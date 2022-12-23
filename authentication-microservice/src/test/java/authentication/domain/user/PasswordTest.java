package authentication.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PasswordTest {

    @Test
    void testToString() {
        Password p = new Password("kekw");
        assertEquals(p.toString(), "kekw");
    }
}