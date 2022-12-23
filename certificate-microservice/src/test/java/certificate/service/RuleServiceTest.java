package certificate.service;

import static org.assertj.core.api.Assertions.assertThat;

import certificate.domain.entities.Certificate;
import certificate.domain.entities.Rule;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class RuleServiceTest {

    @Autowired
    private RuleService ruleService;

    @Test
    void save() {
        List<Rule> empty = ruleService.getAllCertificates();
        assertThat(empty.size()).isEqualTo(0);

        Rule r = new Rule(1, 2);
        ruleService.save(r);
        List<Rule> rules = ruleService.getAllCertificates();
        assertThat(rules.size()).isEqualTo(1);
        assertThat(rules.contains(r)).isTrue();
    }

    @Test
    void getAllMatching() {
        Rule r1 = new Rule(1, 112);
        Rule r2 = new Rule(2, 102);
        Rule r3 = new Rule(3, 440);
        ruleService.save(r1);
        ruleService.save(r2);
        ruleService.save(r3);

        Rule r4 = new Rule(4, 1);
        Rule r5 = new Rule(5, 1121);
        ruleService.save(r4);
        ruleService.save(r5);

        List<Long> ids = ruleService.getAllMatching(new Certificate("kek", 112));

        assertThat(ids.contains(1L)).isTrue();
        assertThat(ids.contains(2L)).isTrue();
    }
}