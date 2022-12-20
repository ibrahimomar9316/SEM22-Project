package certificate.domain;

import certificate.domain.entities.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for the EventRepository.
 */
public interface CertificateRepository extends JpaRepository<Certificate, Long> {

}
