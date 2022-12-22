package user.service;

import java.util.List;
import java.util.Optional;
import javassist.NotFoundException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import user.authentication.AuthManager;
import user.domain.AppUserRepository;
import user.domain.entities.AppUser;
import user.domain.objects.AvDates;
import user.domain.objects.AvDatesDto;

/**
 * App user service implementation of the required methods for the service layer.
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {
    private final AppUserRepository appUserRepository;
    private final transient AuthManager authManager;

    /**
     * Service layer method for getting the specific user by their ID.
     *
     * @param appUserId user ID
     * @return returns a found user.
     * @throws NotFoundException thrown when user not found
     */
    public AppUser getAppUser(String appUserId) throws NotFoundException {
        Optional<AppUser> appUserOptional = appUserRepository.findById(appUserId);
        if (appUserOptional.isEmpty()) {
            throw new NotFoundException("App user not found in the database.");
        }
        return appUserOptional.get();
    }

    /**
     * Service layer method for saving an app user.
     *
     * @param appUser app user body
     * @return saved app user
     */
    public AppUser saveAppUser(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    /**
     * Service layer method for finding all app users.
     *
     * @return - returns a list of all app users
     */
    public List<AppUser> getAppUsers() {
        return appUserRepository.findAll();
    }

    /**
     * Service layer method for updating user information.
     * The method has restriction on changing user's netId and password.
     * While the netId is unchangeable, the user can change his password in another endpoint.
     *
     * @param appUser user update information body
     * @return updated user
     */
    public AppUser updateUser(AppUser appUser) {
        AppUser currentUser = appUserRepository.getAppUserById(authManager.getNetId());
        if (!currentUser.getNetId().equals(appUser.getNetId())) {
            throw new IllegalArgumentException("netId or password can not be update in this method");
        }
        if (appUser.getGender() == null || appUser.getCertificate() == null || appUser.getPrefPosition() == null) {
            throw new IllegalArgumentException("One of the values to be updated was not specified!");
        }
        currentUser.setGender(appUser.getGender());
        currentUser.setCertificate(appUser.getCertificate());
        currentUser.setCompetitive(appUser.isCompetitive());
        currentUser.setPrefPosition(appUser.getPrefPosition());
        appUserRepository.save(currentUser);
        return appUser;
    }
    public void addNewAvDates(AvDatesDto availability) {
        AppUser appUser = appUserRepository.getAppUserById(authManager.getNetId());
        appUser.getAvDatesList().add(new AvDates(availability.getDateFrom(),availability.getDateTo()));
    }

    public List<AvDates> getAvDates() {
        AppUser appUser = appUserRepository.getAppUserById(authManager.getNetId());
        return appUser.getAvDatesList();
    }
}
