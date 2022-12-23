package event.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import event.foreigndomain.enums.Certificate;
import event.foreigndomain.enums.Gender;
import org.junit.jupiter.api.Test;

class EventRulesModelTest {

    @Test
    void testConstructorAndGetters() {
        long eventId =  1L;
        Gender genderConstraint = Gender.MALE;
        boolean professional =  true;
        Certificate certificate = Certificate.C4;
        EventRulesModel eventRulesModel = new EventRulesModel(eventId, genderConstraint, professional, certificate);

        assertEquals(eventId, eventRulesModel.getEventId());
        assertEquals(genderConstraint, eventRulesModel.getGenderConstraint());
        assertEquals(professional, eventRulesModel.isProfessional());
        assertEquals(certificate, eventRulesModel.getCertificate());

        eventRulesModel.setEventId(2L);
        eventRulesModel.setGenderConstraint(Gender.FEMALE);
        eventRulesModel.setProfessional(false);
        eventRulesModel.setCertificate(Certificate.FOURPLUS);

        assertNotEquals(eventId, eventRulesModel.getEventId());
        assertNotEquals(genderConstraint, eventRulesModel.getGenderConstraint());
        assertNotEquals(professional, eventRulesModel.isProfessional());
        assertNotEquals(certificate, eventRulesModel.getCertificate());
    }
}
