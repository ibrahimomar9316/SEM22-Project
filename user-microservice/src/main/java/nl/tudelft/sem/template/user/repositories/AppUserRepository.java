package nl.tudelft.sem.template.user.repositories;

import nl.tudelft.sem.template.user.domain.entities.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository for the app user entity
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Query(value = "SELECT u FROM AppUser u WHERE u.netId = ?1")
    AppUser getAppUserByID(String netId);
}
