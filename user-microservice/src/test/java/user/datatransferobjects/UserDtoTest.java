package user.datatransferobjects;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;


public class UserDtoTest {
    @Test
    public void testHashCode() {
        UserDto user1 = new UserDto("user1");
        UserDto user2 = new UserDto("user2");
        UserDto user3 = new UserDto("user1");

        assertEquals(user1.hashCode(), user3.hashCode());
        assertEquals(user1.hashCode(), user1.hashCode());
        assertEquals(user2.hashCode(), user2.hashCode());

        assertNotEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user2.hashCode(), user3.hashCode());
    }

    @Test
    public void testEquals() {
        UserDto user1 = new UserDto("user1");
        UserDto user2 = new UserDto("user2");
        UserDto user3 = new UserDto("user1");

        assertEquals(user1, user3);
        assertEquals(user1, user1);
        assertEquals(user2, user2);

        assertNotEquals(user1, null);
        assertNotEquals(user1, 2);
        assertNotEquals(user2, user3);
    }

    @Test
    public void testGetterAndSetter() {
        String username = "john.doe";
        UserDto dto = new UserDto();

        dto.setUsername(username);
        assertEquals(username, dto.getUsername());
    }
}
