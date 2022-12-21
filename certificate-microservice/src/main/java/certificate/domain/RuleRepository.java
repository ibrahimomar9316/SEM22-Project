package certificate.domain;


import certificate.domain.entities.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for the CertificateRepository.
 */
@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {

}
