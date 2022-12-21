package certificate.domain;


import certificate.domain.entities.Certificate;
import certificate.domain.entities.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Interface for the CertificateRepository.
 */
@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {
    @Query("SELECT r FROM Rule r WHERE r.eventId = ?1")
    Rule getRuleBy(long eventId);
}
