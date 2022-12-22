package event.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import event.authentication.AuthManager;
import event.domain.EventRepository;
import event.domain.entities.Event;
import event.domain.enums.EventType;
import event.domain.enums.Rule;
import event.domain.objects.Participant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private AuthManager authManager;

    @InjectMocks
    private EventService eventService;

    @Test
    void getEventTest() throws NotFoundException {

        long eventId = 342;
        final String admin = "ezi.boi";
        final List<Participant> participantList = new ArrayList<>();
        final Participant participant1 = new Participant();
        participantList.add(participant1);
        final Rule rule1 = Rule.MALE_ONLY;
        final List<Rule> rules = new ArrayList<>();
        rules.add(rule1);

        final Event eventTest = new Event();
        eventTest.setEventId(eventId);
        eventTest.setEventType(EventType.COMPETITION);
        eventTest.setAdmin(admin);
        eventTest.setParticipants(participantList);
        eventTest.setRules(rules);
        eventTest.setTime(LocalDateTime.now());

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(eventTest));

        final Event res = eventService.getEvent(eventId);
        assertEquals(res, eventTest);
    }

    @Test
    void getEventNotFoundTest() throws NotFoundException {

        long eventId = 123154;
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> eventService.getEvent(eventId));
    }

    @Test
    void saveEventTest() {
        long eventId = 3442;
        EventType eventType = EventType.TRAINING;
        String admin = "ezi.boii";
        LocalDateTime time = LocalDateTime.now();
        List<Participant> participantList = new ArrayList<>();
        Participant participant1 = new Participant();
        participantList.add(participant1);
        Rule rule1 = Rule.MINIMUM_C4;
        Rule rule2 = Rule.ONLY_PROFESSIONAL;
        List<Rule> rules = new ArrayList<>();
        rules.add(rule1);
        rules.add(rule2);

        Event eventTest = new Event();
        eventTest.setEventId(eventId);
        eventTest.setEventType(eventType);
        eventTest.setAdmin(admin);
        eventTest.setParticipants(participantList);
        eventTest.setRules(rules);
        eventTest.setTime(time);

        when(eventRepository.save(eventTest)).thenReturn(eventTest);

        Event res = eventService.saveEvent(eventTest);
        assertEquals(res,eventTest);
    }
}