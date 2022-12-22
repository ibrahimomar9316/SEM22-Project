package event.datatransferobjects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import event.foreigndomain.enums.Gender;
import org.junit.jupiter.api.Test;

class RuleDtoTest {
    @Test
    public void testGetEventId() {
        long eventId = 12345L;

        RuleDto ruleDto = new RuleDto(eventId, Gender.MALE, false, "C4");

        assertEquals(eventId, ruleDto.getEventId());
    }

    @Test
    public void testSetEventId() {
        long eventId1 = 12345L;
        long eventId2 = 54321L;

        RuleDto ruleDto = new RuleDto(eventId1, Gender.MALE, false, "C4");

        ruleDto.setEventId(eventId2);
        assertEquals(eventId2, ruleDto.getEventId());
    }

    @Test
    public void testGetGenderConstraint() {
        Gender genderConstraint = Gender.MALE;

        RuleDto ruleDto = new RuleDto(12345L, genderConstraint, false, "C4");

        assertEquals(genderConstraint, ruleDto.getGenderConstraint());
    }

    @Test
    public void testSetGenderConstraint() {
        Gender genderConstraint1 = Gender.MALE;
        Gender genderConstraint2 = Gender.FEMALE;

        RuleDto ruleDto = new RuleDto(12345L, genderConstraint1, false, "C4");

        ruleDto.setGenderConstraint(genderConstraint2);
        assertEquals(genderConstraint2, ruleDto.getGenderConstraint());
    }

    @Test
    public void testIsPro() {
        boolean pro = true;

        RuleDto ruleDto = new RuleDto(12345L, Gender.MALE, pro, "C4");

        assertEquals(pro, ruleDto.isPro());
    }

    @Test
    public void testSetPro() {
        boolean pro1 = true;
        boolean pro2 = false;

        RuleDto ruleDto = new RuleDto(12345L, Gender.MALE, pro1, "First Aid");

        ruleDto.setPro(pro2);
        assertEquals(pro2, ruleDto.isPro());
    }

    @Test
    public void testGetCertificate() {
        String certificate = "C4";

        RuleDto ruleDto = new RuleDto(12345L, Gender.MALE, false, certificate);

        assertEquals(certificate, ruleDto.getCertificate());
    }

    @Test
    public void testSetCertificate() {
        String certificate1 = "C4";
        String certificate2 = "FOURPLUS";

        RuleDto ruleDto = new RuleDto(12345L, Gender.MALE, false, certificate1);

        ruleDto.setCertificate(certificate2);
        assertEquals(certificate2, ruleDto.getCertificate());
    }

    @Test
    public void testEquals() {
        long eventId = 12345L;
        Gender genderConstraint = Gender.MALE;
        boolean pro = false;
        String certificate = "C4";

        RuleDto ruleDto1 = new RuleDto(eventId, genderConstraint, pro, certificate);
        RuleDto ruleDto2 = new RuleDto(eventId, genderConstraint, pro, certificate);

        assertEquals(ruleDto1, ruleDto2);
    }

    @Test
    public void testNotEquals_DifferentEventId() {
        long eventId1 = 12345L;
        long eventId2 = 54321L;
        Gender genderConstraint = Gender.MALE;
        boolean pro = false;
        String certificate = "C4";

        RuleDto ruleDto1 = new RuleDto(eventId1, genderConstraint, pro, certificate);
        RuleDto ruleDto2 = new RuleDto(eventId2, genderConstraint, pro, certificate);

        assertThat(ruleDto1).isNotEqualTo(ruleDto2);
    }

    @Test
    public void testNotEquals_DifferentGenderConstraint() {
        long eventId = 12345L;
        Gender genderConstraint1 = Gender.MALE;
        Gender genderConstraint2 = Gender.FEMALE;
        boolean pro = false;
        String certificate = "C4";

        RuleDto ruleDto1 = new RuleDto(eventId, genderConstraint1, pro, certificate);
        RuleDto ruleDto2 = new RuleDto(eventId, genderConstraint2, pro, certificate);

        assertThat(ruleDto1).isNotEqualTo(ruleDto2);
    }

    @Test
    public void testNotEquals_DifferentPro() {
        long eventId = 12345L;
        Gender genderConstraint = Gender.MALE;
        boolean pro1 = false;
        boolean pro2 = true;
        String certificate = "C4";

        RuleDto ruleDto1 = new RuleDto(eventId, genderConstraint, pro1, certificate);
        RuleDto ruleDto2 = new RuleDto(eventId, genderConstraint, pro2, certificate);

        assertThat(ruleDto1).isNotEqualTo(ruleDto2);
    }

    @Test
    public void testNotEquals_DifferentCertificate() {
        long eventId = 12345L;
        Gender genderConstraint = Gender.MALE;
        boolean pro = false;
        String certificate1 = "C4";
        String certificate2 = "FOURPLUS";

        RuleDto ruleDto1 = new RuleDto(eventId, genderConstraint, pro, certificate1);
        RuleDto ruleDto2 = new RuleDto(eventId, genderConstraint, pro, certificate2);

        assertThat(ruleDto1).isNotEqualTo(ruleDto2);
    }
}

