package user.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import user.authentication.AuthManager;
import user.domain.entities.AppUser;
import user.domain.enums.Gender;
import user.foreigndomain.enums.Certificate;
import user.foreigndomain.enums.Position;
import user.models.UserDetailsModel;
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
    public void testSaveUser() {
        AppUser savedUser = new AppUser("user");
        when(appUserService.saveAppUser(savedUser)).thenReturn(savedUser);
        when(auth.getNetId()).thenReturn("user");

        ResponseEntity<String> response = userController.saveUser();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), "user");
    }

    @Test
    public void updateUserTest() throws Exception {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        userController.setRestTemplate(restTemplate);
        List<LocalDateTime> list = new ArrayList<>();
        UserDetailsModel request = new UserDetailsModel(Gender.MALE, Position.COX, true, Certificate.C4, list);
        when(restTemplate.postForEntity(anyString(), any(), any())).thenReturn(ResponseEntity.ok().body(200));

        ResponseEntity<String> res = userController.updateUser("token k", request);
        assertThat(res.getStatusCode().value()).isEqualTo(200);
        assertThat(Objects.requireNonNull(res.getBody()).contains("Successfully updated user:\n")).isTrue();

        UserDetailsModel request2 = new UserDetailsModel(Gender.FEMALE, Position.COX, true, Certificate.C4, list);
        when(restTemplate.postForEntity(anyString(), any(), any())).thenReturn(ResponseEntity.ok().body(404));
        ResponseEntity<String> res2 = userController.updateUser("token l", request2);
        assertThat(res2.getStatusCode().value()).isEqualTo(400);
        assertThat(res2.getBody()).isEqualTo("Error in generating index!");

        UserDetailsModel request3 = new UserDetailsModel(Gender.FEMALE, Position.COX, true, Certificate.C4, list);
        when(restTemplate.postForEntity(anyString(), any(), any()))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(300));
        ResponseEntity<String> res3 = userController.updateUser("token l", request3);
        assertThat(res3.getStatusCode().value()).isEqualTo(404);
    }

}
