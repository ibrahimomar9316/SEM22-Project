package user.datatransferobjects;


import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserDtoTest {
    @Test
    public void testUserDto() {
        String username = "john.doe";
        UserDto dto1 = new UserDto(username);
        UserDto dto2 = new UserDto(username);

        // dto1 and dto2 should be equal
        assertEquals(dto1, dto2);

        // change the username in dto2 and verify that dto1 and dto2 are no longer equal
        dto2.setUsername("jane.doe");
        assertNotEquals(dto1, dto2);
    }

    @Test
    public void testGetterAndSetter() {
        String username = "john.doe";
        UserDto dto = new UserDto();

        dto.setUsername(username);
        assertEquals(username, dto.getUsername());
    }
}
