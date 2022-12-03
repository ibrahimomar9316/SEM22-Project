package nl.tudelft.sem.template.user.service;

import javassist.NotFoundException;
import nl.tudelft.sem.template.user.domain.entities.AppUser;
import nl.tudelft.sem.template.user.domain.entities.Boat;
import nl.tudelft.sem.template.user.domain.entities.Certificate;
import nl.tudelft.sem.template.user.domain.entities.Role;

import java.util.List;

/**
 * App user service defining methods to implement for the correct service layer
 */
public interface AppUserService {
    AppUser getAppUser(Long appUserId) throws NotFoundException;

    AppUser saveAppUser(AppUser appUser);

    Boat saveBoat(Boat boat);

    Certificate saveCertificate(Certificate certificate);

    Role saveRole(Role role);

    void addBoatToAppUser(Long appUserId, Long boatId) throws NotFoundException;

    void addCertificateToAppUser(Long appUserId, Long certificateId) throws NotFoundException;

    void addRoleToAppUser(Long appUserId, Long roleId) throws NotFoundException;

    List<AppUser> getAppUsers();

    List<Role> getRoles();
}
