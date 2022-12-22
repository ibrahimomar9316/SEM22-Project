package certificate.domain;

import certificate.domain.entities.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository for the Certificate entity extending the JpaRepository class.
 */
@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    /**
     * Query used to retrieve one certificate from the DataBase using the netId of the user having
     * the hashed preferences.
     *
     * @param netId the netId of the user we want to retrieve the hashed preferences
     * @return the Certificate that was stored in the DB
     */
    @Query("SELECT c FROM Certificate c WHERE c.netId = ?1")
    Certificate getCertificateBy(String netId);
}
