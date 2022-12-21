package certificate.service;

import certificate.domain.RuleRepository;
import certificate.domain.entities.Rule;
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
}
