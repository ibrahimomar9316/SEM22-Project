package certificate.domain;

import certificate.domain.entities.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository for the Rule entity extending the JpaRepository class.
 */
@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {

    /**
     * Query used to retrieve the Rules from the DataBase using the eventId of the event having the hashed rules.
     *
     * @param eventId the eventId of the event we want to retrieve the hashed rules
     * @return the hashed rules that were stored in the DB
     */
    @Query("SELECT r FROM Rule r WHERE r.eventId = ?1")
    Rule getRuleBy(long eventId);
}
