package certificate.service;

import certificate.domain.RuleRepository;
import certificate.domain.entities.Rule;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The type Rule service.
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RuleService {
    private final RuleRepository ruleRepository;

    /**
     * Save rule.
     *
     * @param rule the rule
     */
    public void save(Rule rule) {
        ruleRepository.save(rule);
    }

    /**
     * Gets all rules.
     *
     * @return the all rules
     */
    public List<Rule> getAllCertificates() {
        return ruleRepository.findAll();
    }
}
