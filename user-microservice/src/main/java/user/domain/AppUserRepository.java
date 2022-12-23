package user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import user.domain.entities.AppUser;

/**
 * Repository for the app user entity extending the JpaRepository class.
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {

    /**
     * Query used to retrieve one user from the DataBase using the netId of that user.
     *
     * @param netId the netId of the user we want to retrieve
     * @return the AppUser that was stored in the DB
     */
    @Query(value = "SELECT u FROM AppUser u WHERE u.netId = ?1")
    AppUser getAppUserById(String netId);
}
