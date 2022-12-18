package user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import user.domain.entities.AppUser;

/**
 * Repository for the app user entity.
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Query(value = "SELECT u FROM AppUser u WHERE u.netId = ?1")
    AppUser getAppUserById(String netId);
}
