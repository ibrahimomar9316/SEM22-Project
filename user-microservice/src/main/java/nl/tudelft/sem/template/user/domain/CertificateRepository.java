package nl.tudelft.sem.template.user.domain;

import nl.tudelft.sem.template.user.domain.entities.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the certificate entity.
 */
@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
