package event.domain.enums;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class RuleTest {

    @Test
    void valuesTest() {
        assertThat(Rule.ONLY_PROFESSIONAL)
                .isEqualTo(Rule.ONLY_PROFESSIONAL);
        assertThat(Rule.SAME_GENDER_TEAMS)
                .isEqualTo(Rule.SAME_GENDER_TEAMS);
        assertThat(Rule.FEMALE_ONLY)
                .isEqualTo(Rule.FEMALE_ONLY);
        assertThat(Rule.MALE_ONLY)
                .isEqualTo(Rule.MALE_ONLY);
        assertThat(Rule.MINIMUM_C4)
                .isEqualTo(Rule.MINIMUM_C4);
        assertThat(Rule.MINIMUM_FOURPLUS)
                .isEqualTo(Rule.MINIMUM_FOURPLUS);
        assertThat(Rule.MINIMUM_EIGHTPLUS)
                .isEqualTo(Rule.MINIMUM_EIGHTPLUS);
    }
}
