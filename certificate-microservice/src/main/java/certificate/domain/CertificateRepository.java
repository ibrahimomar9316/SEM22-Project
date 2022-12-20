package certificate.domain;

import certificate.domain.entities.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for the CertificateRepository.
 */
@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {

}
