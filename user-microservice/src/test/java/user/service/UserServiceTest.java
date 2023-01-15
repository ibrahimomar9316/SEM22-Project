package user.service;

import static org.assertj.core.api.Assertions.assertThat;
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
        LocalDateTime from = LocalDateTime.MIN;
        LocalDateTime to = LocalDateTime.MAX;


        AppUser appUser = new AppUser(netId);
        appUser.upDate(
                gender,
                prefPosition,
                competitive,
                certificate,
                from,
                to
        );

        when(appUserRepository.findById(netId)).thenReturn(Optional.of(appUser));

        AppUser result = userService.getAppUser(netId);
        assertEquals(appUser, result);
    }

    @Test
    public void testGetAppUserNotFound() {
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
        LocalDateTime from = LocalDateTime.MIN;
        LocalDateTime to = LocalDateTime.MAX;

        AppUser appUser = new AppUser(netId);
        appUser.upDate(
                gender,
                prefPosition,
                competitive,
                certificate,
                from,
                to
        );

        when(appUserRepository.save(appUser)).thenReturn(appUser);

        AppUser result = userService.saveAppUser(appUser);
        assertEquals(appUser, result);
    }

    @Test
    public void testGetAppUsers() {
        String netId = "test123";
        Gender gender = Gender.MALE;
        Position prefPosition = Position.COX;
        boolean competitive = true;
        Certificate certificate = Certificate.FOURPLUS;
        LocalDateTime from = LocalDateTime.MIN;
        LocalDateTime to = LocalDateTime.MAX;

        AppUser appUser = new AppUser(netId);
        appUser.upDate(
                gender,
                prefPosition,
                competitive,
                certificate,
                from,
                to
        );

        String netId2 = "test456";
        Gender gender2 = Gender.FEMALE;
        Position prefPosition2 = Position.COACH;
        boolean competitive2 = false;
        Certificate certificate2 = Certificate.C4;
        LocalDateTime from2 = LocalDateTime.MIN;
        LocalDateTime to2 = LocalDateTime.MAX;

        AppUser appUser2 = new AppUser(netId2);
        appUser2.upDate(
                gender2,
                prefPosition2,
                competitive2,
                certificate2,
                from2,
                to2
        );

        when(appUserRepository.findAll()).thenReturn(Arrays.asList(appUser, appUser2));

        List<AppUser> result = userService.getAppUsers();
        assertEquals(2, result.size());
        assertEquals(appUser, result.get(0));
        assertEquals(appUser2, result.get(1));
    }

    @Test
    public void testUpdateUser() {
        String netId = "test123";
        Gender gender = Gender.MALE;
        Position prefPosition = Position.COX;
        boolean competitive = true;
        Certificate certificate = Certificate.NONE;
        LocalDateTime from = LocalDateTime.MIN;
        LocalDateTime to = LocalDateTime.MAX;

        AppUser appUser = new AppUser(netId);
        appUser.upDate(
                gender,
                prefPosition,
                competitive,
                certificate,
                from,
                to
        );

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
        LocalDateTime from = LocalDateTime.MIN;
        LocalDateTime to = LocalDateTime.MAX;

        AppUser appUser = new AppUser(newNetId);
        appUser.upDate(
                gender,
                prefPosition,
                competitive,
                certificate,
                from,
                to
        );

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
        appUser.upDate(
                null,
                null,
                competitive,
                null,
                null,
                null
        );

        when(authManager.getNetId()).thenReturn(netId);
        when(appUserRepository.getAppUserById(netId)).thenReturn(appUser);

        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(appUser));

        AppUser appUser1 = new AppUser(netId, null, null, true, null, null, null);
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(appUser1));

        AppUser appUser2 = new AppUser(netId, Gender.MALE, null, true, null, null, null);
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(appUser2));

        AppUser appUser3 = new AppUser(netId, Gender.MALE, null, true, Certificate.C4, null, null);
        when(authManager.getNetId()).thenReturn(netId);
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(appUser3));
    }

    @Test
    public void testUpdateUserWrongId() {
        String netId = "test123";
        boolean competitive = true;

        AppUser appUser = new AppUser(netId);
        appUser.upDate(
                null,
                null,
                competitive,
                null,
                null,
                null
        );

        when(authManager.getNetId()).thenReturn(netId);
        when(appUserRepository.getAppUserById(netId)).thenReturn(appUser);

        AppUser appUser1 = new AppUser("wrong");
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(appUser1));
    }

    @Test
    public void getAvDatesTest() {
        String netId = "test123";
        Gender gender = Gender.MALE;
        Position prefPosition = Position.COACH;
        boolean competitive = true;
        Certificate certificate = Certificate.NONE;
        LocalDateTime from = LocalDateTime.MIN;
        LocalDateTime to = LocalDateTime.MAX;

        AppUser user = new AppUser(netId);
        user.upDate(
                gender,
                prefPosition,
                competitive,
                certificate,
                from,
                to
        );

        when(authManager.getNetId()).thenReturn("user");
        when(appUserRepository.getAppUserById("user")).thenReturn(user);

        assertThat(userService.getUserAvailability().getAvailableFrom()).isEqualTo(from);
        assertThat(userService.getUserAvailability().getAvailableTo()).isEqualTo(to);
    }

}



