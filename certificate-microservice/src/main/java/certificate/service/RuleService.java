package certificate.service;

import certificate.domain.RuleRepository;
import certificate.domain.entities.Certificate;
import certificate.domain.entities.Rule;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The service used to operate on the hashed Rules database.
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RuleService {
    private final RuleRepository ruleRepository;

    /**
     * Function used to save the hashed rules in the database.
     *
     * @param rule the hashed rule we want to store in the database
     */
    public void save(Rule rule) {
        ruleRepository.save(rule);
    }

    /**
     * Getter function to retrieve all hashed rules for all the events.
     *
     * @return all events with their corresponding hashed rules
     */
    public List<Rule> getAllCertificates() {
        return ruleRepository.findAll();
    }

    public List<Long> getAllMatching(Certificate certificate) {
        List<Rule> list = ruleRepository.findAll();
        return  list.stream().filter(x -> checkConstraints(certificate,x))
                .map(Rule::getEventId).collect(Collectors.toList());
    }

    private boolean checkConstraints(Certificate certificate, Rule rule) {
        int index = 0;
        String ruleInd = String.valueOf(rule.getRuleIndex());
        String certificateInd = String.valueOf(certificate.getCertificateIndex());
        while (index < ruleInd.length() && index < certificateInd.length()) {
            if (ruleInd.charAt(index) == '0') {
                index++;
                continue;
            } else if (ruleInd.charAt(index) != certificateInd.charAt(index)) {
                return false;
            }
            index++;
        }
        return true;
    }

}
