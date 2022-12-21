package user.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import user.authentication.AuthManager;
import user.datatransferobjects.UserDto;
import user.domain.entities.AppUser;
import user.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService appUserService;

    @Mock
    private AuthManager auth;


    @InjectMocks
    private UserController userController;



    @Test
    public void testGetUsers() {
        AppUser appUser1 = new AppUser("test123");
        AppUser appUser2 = new AppUser("test456");
        when(appUserService.getAppUsers()).thenReturn(Arrays.asList(appUser1, appUser2));

        ResponseEntity<List<AppUser>> response = userController.getUsers();
        List<AppUser> result = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, result.size());
        assertEquals(appUser1, result.get(0));
        assertEquals(appUser2, result.get(1));
    }

    @Test
    public void testSaveUser() throws NotFoundException {
        UserDto userDto = new UserDto("test123");
        AppUser savedUser = new AppUser(userDto.getUsername());
        when(appUserService.saveAppUser(savedUser)).thenReturn(savedUser);

        ResponseEntity<UserDto> response = userController.saveUser(userDto);
        UserDto result = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userDto, result);
    }

}
