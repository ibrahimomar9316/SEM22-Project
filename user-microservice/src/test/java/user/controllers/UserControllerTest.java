package user.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import user.authentication.AuthManager;
import user.datatransferobjects.UserCertificateDto;
import user.datatransferobjects.UserDto;
import user.domain.entities.AppUser;
import user.domain.enums.Certificate;
import user.domain.enums.Gender;
import user.domain.enums.Position;
import user.models.UserDetailsModel;
import user.service.UserService;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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



/*    @Test
    public void testUpdateUser() throws Exception {
        String netId = "test123";
        Gender gender = Gender.MALE;
        Position prefPosition = Position.COX;
        boolean competitive = true;
        Certificate certificate = Certificate.C4;
        List<LocalDateTime> avDates = Arrays.asList(LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        AppUser appUser = new AppUser(netId);
        appUser.setGender(gender);
        appUser.setPrefPosition(prefPosition);
        appUser.setCompetitive(competitive);
        appUser.setCertificate(certificate);
        appUser.setAvDates(avDates);

        UserDetailsModel request = new UserDetailsModel(gender, prefPosition,
                competitive, certificate, avDates);

        when(auth.getNetId()).thenReturn(netId);
        when(appUserService.updateUser(appUser)).thenReturn(appUser);

        UserCertificateDto ucd = new UserCertificateDto(
                appUser.getGender() == Gender.MALE,
                appUser.isCompetitive(),
                appUser.getPrefPosition().toString(),
                appUser.getCertificate().toString()
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String json = new ObjectMapper().writeValueAsString(ucd);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        UserCertificateDto userCertificateDto = new UserCertificateDto(true, true, "FORWARD", "FIFA");
        when(new RestTemplate().postForEntity(
                "http://localhost:8084/api/certificate/filter", entity, UserCertificateDto.class
        )).thenReturn(ResponseEntity.ok(userCertificateDto));

        ResponseEntity<String> response = userController.updateUser(request);
        String result = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully updated user:\n" + appUser, result);
    }*/

}
