package nl.tudelft.sem.template.user.service;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.tudelft.sem.template.user.domain.entities.AppUser;
import nl.tudelft.sem.template.user.domain.entities.Boat;
import nl.tudelft.sem.template.user.domain.entities.Certificate;
import nl.tudelft.sem.template.user.domain.entities.Role;
import nl.tudelft.sem.template.user.repositories.AppUserRepository;
import nl.tudelft.sem.template.user.repositories.BoatRepository;
import nl.tudelft.sem.template.user.repositories.CertificateRepository;
import nl.tudelft.sem.template.user.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * App user service implementation of the required methods for the service layer
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImplementation implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final BoatRepository boatRepository;
    private final CertificateRepository certificateRepository;
    private final RoleRepository roleRepository;

    /**
     * Service layer method for getting the specific user by their ID
     * @param appUserId - user ID
     * @return - returns a found user
     * @throws NotFoundException - thrown when user not found
     */
    @Override
    public AppUser getAppUser(Long appUserId) throws NotFoundException {
        Optional<AppUser> appUserOptional = appUserRepository.findById(appUserId);
        if (appUserOptional.isEmpty()) {
            throw new NotFoundException("App user not found in the database.");
        }
        return appUserOptional.get();
    }

    /**
     * Service layer method for saving an app user
     * @param appUser - app user body
     * @return - saved app user
     */
    @Override
    public AppUser saveAppUser(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    /**
     * Service layer method for saving a boat
     * @param boat - boat body
     * @return - saved boat
     */
    @Override
    public Boat saveBoat(Boat boat) {
        return boatRepository.save(boat);
    }

    /**
     * Service layer method for saving a certificate
     * @param certificate - certificate body
     * @return - saved certificate
     */
    @Override
    public Certificate saveCertificate(Certificate certificate) {
        return certificateRepository.save(certificate);
    }

    /**
     * Service layer method for saving a role
     * @param role - role body
     * @return - saved role
     */
    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    /**
     * Service layer method for adding a boat to the user using their IDs
     * @param appUserId - user ID
     * @param boatId - boat ID
     * @throws NotFoundException - either user or boat is not found in the database
     */
    @Override
    public void addBoatToAppUser(Long appUserId, Long boatId) throws NotFoundException {
        AppUser appUser = getAppUser(appUserId);

        Optional<Boat> boatOptional = boatRepository.findById(boatId);
        if (boatOptional.isEmpty()) {
            throw new NotFoundException("Boat not found in the database.");
        }
        Boat boat = boatOptional.get();

        appUser.getBoatTypePreferences().add(boat);
    }

    /**
     * Service layer method for adding a certificate to the user using their IDs
     * @param appUserId - user ID
     * @param certificateId - certificate ID
     * @throws NotFoundException - either user or certificate is not found in the database
     */
    @Override
    public void addCertificateToAppUser(Long appUserId, Long certificateId) throws NotFoundException {
        AppUser appUser = getAppUser(appUserId);

        Optional<Certificate> certificateOptional = certificateRepository.findById(certificateId);
        if (certificateOptional.isEmpty()) {
            throw new NotFoundException("Certificate not found in the database.");
        }
        Certificate certificate = certificateOptional.get();

        appUser.getCertificateCollection().add(certificate);
    }

    /**
     * Service layer method for adding a role to the user using their IDs
     * @param appUserId - user ID
     * @param roleId - role ID
     * @throws NotFoundException - either user or role is not found in the database
     */
    @Override
    public void addRoleToAppUser(Long appUserId, Long roleId) throws NotFoundException {
        AppUser appUser = getAppUser(appUserId);

        Optional<Role> roleOptional = roleRepository.findById(roleId);
        if (roleOptional.isEmpty()) {
            throw new NotFoundException("Role not found in the database.");
        }
        Role role = roleOptional.get();

        appUser.getRoles().add(role);
    }

    /**
     * Service layer method for finding all app users
     * @return - returns a list of all app users
     */
    @Override
    public List<AppUser> getAppUsers() {
        return appUserRepository.findAll();
    }

    /**
     * Service layer method for finding all roles
     * @return - returns a list of all roles
     */
    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
