package user.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AuthTokenHolderTest {

    @Test
    public void testGetterAndSetter() {
        AuthTokenHolder holder = new AuthTokenHolder();
        holder.setToken("my-token");
        assertEquals("my-token", holder.getToken());
    }

    @Test
    public void testNoArgsConstructor() {
        AuthTokenHolder holder = new AuthTokenHolder();
        assertEquals(null, holder.getToken());
    }

    @Test
    public void testAllArgsConstructor() {
        AuthTokenHolder holder = new AuthTokenHolder("my-token");
        assertEquals("my-token", holder.getToken());
    }
}
