package user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import user.authentication.AuthManager;
import user.domain.AppUserRepository;
import user.domain.entities.AppUser;
import user.domain.enums.Gender;
import user.foreigndomain.enums.Certificate;
import user.foreigndomain.enums.Position;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private AuthManager authManager;

    @InjectMocks
    private UserService userService;


    @Test
    public void testGetAppUser() throws NotFoundException {
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

        when(appUserRepository.findById(netId)).thenReturn(Optional.of(appUser));

        AppUser result = userService.getAppUser(netId);
        assertEquals(appUser, result);
    }

    @Test
    public void testGetAppUserNotFound() throws NotFoundException {
        String appUserId = "123";
        when(appUserRepository.findById(appUserId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.getAppUser(appUserId));
    }


    @Test
    public void testSaveAppUser() {
        String netId = "test123";
        Gender gender = Gender.MALE;
        Position prefPosition = Position.COX;
        boolean competitive = true;
        Certificate certificate = Certificate.FOURPLUS;
        List<LocalDateTime> avDates = Arrays.asList(LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        AppUser appUser = new AppUser(netId);
        appUser.setGender(gender);
        appUser.setPrefPosition(prefPosition);
        appUser.setCompetitive(competitive);
        appUser.setCertificate(certificate);
        appUser.setAvDates(avDates);

        when(appUserRepository.save(appUser)).thenReturn(appUser);

        AppUser result = userService.saveAppUser(appUser);
        assertEquals(appUser, result);
    }

    @Test
    public void testGetAppUsers() {
        String netId1 = "test123";
        Gender gender1 = Gender.MALE;
        Position prefPosition1 = Position.COX;
        boolean competitive1 = true;
        Certificate certificate1 = Certificate.NONE;
        List<LocalDateTime> avDates1 = Arrays.asList(LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        AppUser appUser1 = new AppUser(netId1);
        appUser1.setGender(gender1);
        appUser1.setPrefPosition(prefPosition1);
        appUser1.setCompetitive(competitive1);
        appUser1.setCertificate(certificate1);
        appUser1.setAvDates(avDates1);

        String netId2 = "test456";
        Gender gender2 = Gender.FEMALE;
        Position prefPosition2 = Position.COACH;
        boolean competitive2 = false;
        Certificate certificate2 = Certificate.C4;
        List<LocalDateTime> avDates2 = Arrays.asList(LocalDateTime.now(), LocalDateTime.now().plusDays(2));

        AppUser appUser2 = new AppUser(netId2);
        appUser2.setGender(gender2);
        appUser2.setPrefPosition(prefPosition2);
        appUser2.setCompetitive(competitive2);
        appUser2.setCertificate(certificate2);
        appUser2.setAvDates(avDates2);

        when(appUserRepository.findAll()).thenReturn(Arrays.asList(appUser1, appUser2));

        List<AppUser> result = userService.getAppUsers();
        assertEquals(2, result.size());
        assertEquals(appUser1, result.get(0));
        assertEquals(appUser2, result.get(1));
    }

    @Test
    public void testUpdateUser() {
        String netId = "test123";
        Gender gender = Gender.MALE;
        Position prefPosition = Position.COX;
        boolean competitive = true;
        Certificate certificate = Certificate.NONE;
        List<LocalDateTime> avDates = Arrays.asList(LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        AppUser appUser = new AppUser(netId);
        appUser.setGender(gender);
        appUser.setPrefPosition(prefPosition);
        appUser.setCompetitive(competitive);
        appUser.setCertificate(certificate);
        appUser.setAvDates(avDates);

        when(authManager.getNetId()).thenReturn(netId);
        when(appUserRepository.getAppUserById(netId)).thenReturn(appUser);
        when(appUserRepository.save(appUser)).thenReturn(appUser);

        AppUser result = userService.updateUser(appUser);
        assertEquals(appUser, result);
    }

    @Test
    public void testUpdateUserNetId() {
        String newNetId = "test456";
        Gender gender = Gender.MALE;
        Position prefPosition = Position.COX;
        boolean competitive = true;
        Certificate certificate = Certificate.NONE;
        List<LocalDateTime> avDates = Arrays.asList(LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        AppUser appUser = new AppUser(newNetId);
        appUser.setGender(gender);
        appUser.setPrefPosition(prefPosition);
        appUser.setCompetitive(competitive);
        appUser.setCertificate(certificate);
        appUser.setAvDates(avDates);

        String netId = "test123";
        when(authManager.getNetId()).thenReturn(netId);
        when(appUserRepository.getAppUserById(netId)).thenReturn(appUser);

        userService.updateUser(appUser);
    }

    @Test
    public void testUpdateUserNullValues() {
        String netId = "test123";
        boolean competitive = true;

        AppUser appUser = new AppUser(netId);
        appUser.setGender(null);
        appUser.setPrefPosition(null);
        appUser.setCompetitive(competitive);
        appUser.setCertificate(null);
        appUser.setAvDates(null);

        when(authManager.getNetId()).thenReturn(netId);
        when(appUserRepository.getAppUserById(netId)).thenReturn(appUser);

        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(appUser));

        AppUser appUser1 = new AppUser(netId, null, null, true, null, null);
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(appUser1));

        AppUser appUser2 = new AppUser(netId, Gender.MALE, null, true, null, null);
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(appUser2));

        AppUser appUser3 = new AppUser(netId, Gender.MALE, null, true, Certificate.C4, null);
        when(authManager.getNetId()).thenReturn(netId);
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(appUser3));
    }

    @Test
    public void testUpdateUserWrongId() {
        String netId = "test123";
        boolean competitive = true;

        AppUser appUser = new AppUser(netId);
        appUser.setGender(null);
        appUser.setPrefPosition(null);
        appUser.setCompetitive(competitive);
        appUser.setCertificate(null);
        appUser.setAvDates(null);

        when(authManager.getNetId()).thenReturn(netId);
        when(appUserRepository.getAppUserById(netId)).thenReturn(appUser);

        AppUser appUser1 = new AppUser("wrong");
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(appUser1));
    }
}



