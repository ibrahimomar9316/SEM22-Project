package event.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import event.authentication.AuthManager;
import event.domain.EventRepository;
import event.domain.entities.Event;
import event.domain.enums.EventType;
import event.domain.enums.Rule;
import event.domain.objects.Participant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

        final long eventId = 342;
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

        final long eventId = 123154;
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> eventService.getEvent(eventId));
    }

    @Test
    void saveEventTest() {
        final long eventId = 3442;
        final EventType eventType = EventType.TRAINING;
        final String admin = "ezi.boii";
        final LocalDateTime time = LocalDateTime.now();
        final List<Participant> participantList = new ArrayList<>();
        final Participant participant1 = new Participant();
        participantList.add(participant1);
        final Rule rule1 = Rule.MINIMUM_C4;
        final Rule rule2 = Rule.ONLY_PROFESSIONAL;
        final List<Rule> rules = new ArrayList<>();
        rules.add(rule1);
        rules.add(rule2);

        final Event eventTest = new Event();
        eventTest.setEventId(eventId);
        eventTest.setEventType(eventType);
        eventTest.setAdmin(admin);
        eventTest.setParticipants(participantList);
        eventTest.setRules(rules);
        eventTest.setTime(time);

        when(eventRepository.save(eventTest)).thenReturn(eventTest);

        final Event res = eventService.saveEvent(eventTest);
        assertEquals(res, eventTest);
    }

    @Test
    void updateEventTest() {
        final long eventId = 344322;
        final EventType eventType = EventType.COMPETITION;
        final String admin = "ezi.boisul";
        final LocalDateTime time = LocalDateTime.now();
        List<Participant> participantList = new ArrayList<>();
        Participant participant1 = new Participant();
        participantList.add(participant1);
        final Rule rule1 = Rule.MINIMUM_C4;
        final Rule rule2 = Rule.ONLY_PROFESSIONAL;
        final List<Rule> rules = new ArrayList<>();
        rules.add(rule1);
        rules.add(rule2);

        final Event eventTest = new Event();
        eventTest.setEventId(eventId);
        eventTest.setEventType(eventType);
        eventTest.setAdmin(admin);
        eventTest.setParticipants(participantList);
        eventTest.setRules(rules);
        eventTest.setTime(time);

        when(eventRepository.saveAndFlush(eventTest)).thenReturn(eventTest);

        final Event res = eventService.updateEvent(eventTest);
        assertEquals(res, eventTest);
    }

    @Test
    void getAllEventsTest() {
        final long eventId = 344322;
        final EventType eventType = EventType.COMPETITION;
        final String admin = "ezi.boiul";
        final LocalDateTime time = LocalDateTime.now();
        List<Participant> participantList = new ArrayList<>();
        Participant participant1 = new Participant();
        participantList.add(participant1);
        final Rule rule1 = Rule.MINIMUM_C4;
        final Rule rule2 = Rule.ONLY_PROFESSIONAL;
        List<Rule> rules = new ArrayList<>();
        rules.add(rule1);
        rules.add(rule2);

        Event eventTest1 = new Event();
        eventTest1.setEventId(eventId);
        eventTest1.setEventType(eventType);
        eventTest1.setAdmin(admin);
        eventTest1.setParticipants(participantList);
        eventTest1.setRules(rules);
        eventTest1.setTime(time);


        long eventId2 = 342;
        final String admin2 = "ezi.boi";
        final List<Participant> participantList2 = new ArrayList<>();
        final Participant participant12 = new Participant();
        participantList.add(participant12);
        final Rule rule12 = Rule.MALE_ONLY;
        final List<Rule> rules2 = new ArrayList<>();
        rules.add(rule12);

        final Event eventTest2 = new Event();
        eventTest2.setEventId(eventId2);
        eventTest2.setEventType(EventType.COMPETITION);
        eventTest2.setAdmin(admin2);
        eventTest2.setParticipants(participantList2);
        eventTest2.setRules(rules2);
        eventTest2.setTime(LocalDateTime.now());

        when(eventRepository.findAll()).thenReturn(Arrays.asList(eventTest1, eventTest2));

        List<Event> res = eventService.getAllEvents();
        assertEquals(2, res.size());
        assertEquals(eventTest1, res.get(0));
        assertEquals(eventTest2, res.get(1));
    }

    @Test
    void getEventsByAdminTest() {
        final long eventId = 344322;
        final EventType eventType = EventType.COMPETITION;
        final String admin = "ezi.boiul";
        final LocalDateTime time = LocalDateTime.now();
        List<Participant> participantList = new ArrayList<>();
        Participant participant1 = new Participant();
        participantList.add(participant1);
        final Rule rule1 = Rule.MINIMUM_C4;
        final Rule rule2 = Rule.ONLY_PROFESSIONAL;
        List<Rule> rules = new ArrayList<>();
        rules.add(rule1);
        rules.add(rule2);

        Event eventTest1 = new Event();
        eventTest1.setEventId(eventId);
        eventTest1.setEventType(eventType);
        eventTest1.setAdmin(admin);
        eventTest1.setParticipants(participantList);
        eventTest1.setRules(rules);
        eventTest1.setTime(time);


        long eventId2 = 342;
        final String admin2 = "ezi.boi";
        final List<Participant> participantList2 = new ArrayList<>();
        final Participant participant12 = new Participant();
        participantList.add(participant12);
        final Rule rule12 = Rule.MALE_ONLY;
        final List<Rule> rules2 = new ArrayList<>();
        rules.add(rule12);

        final Event eventTest2 = new Event();
        eventTest2.setEventId(eventId2);
        eventTest2.setEventType(EventType.COMPETITION);
        eventTest2.setAdmin(admin2);
        eventTest2.setParticipants(participantList2);
        eventTest2.setRules(rules2);
        eventTest2.setTime(LocalDateTime.now());

        final long eventId3 = 34332;
        final String admin3 = "ezi.boiul";
        final List<Participant> participantList3 = new ArrayList<>();
        final Participant participant13 = new Participant();
        participantList.add(participant13);
        participantList.add(participant13);
        final Rule rule13 = Rule.MALE_ONLY;
        final List<Rule> rules3 = new ArrayList<>();
        rules.add(rule13);

        final Event eventTest3 = new Event();
        eventTest3.setEventId(eventId3);
        eventTest3.setEventType(EventType.COMPETITION);
        eventTest3.setAdmin(admin3);
        eventTest3.setParticipants(participantList3);
        eventTest3.setRules(rules3);
        eventTest3.setTime(LocalDateTime.now());

        when(eventRepository.getEventsByAdmin(admin)).thenReturn(Arrays.asList(eventTest1, eventTest3));

        List<Event> res = eventService.getEventsByAdmin(admin);
        assertEquals(2, res.size());
        assertEquals(eventTest1, res.get(0));
        assertEquals(eventTest3, res.get(1));
    }

    @Test
    void getEventsByParticipantTest() {
        final long eventId = 344322;
        final EventType eventType = EventType.COMPETITION;
        final String admin = "ezi.boiul";
        final LocalDateTime time = LocalDateTime.now();
        List<Participant> participantList = new ArrayList<>();
        Participant participant1 = new Participant();
        participant1.setNetId("P1");
        participantList.add(participant1);
        final Rule rule1 = Rule.MINIMUM_C4;
        final Rule rule2 = Rule.ONLY_PROFESSIONAL;
        List<Rule> rules = new ArrayList<>();
        rules.add(rule1);
        rules.add(rule2);

        Event eventTest1 = new Event();
        eventTest1.setEventId(eventId);
        eventTest1.setEventType(eventType);
        eventTest1.setAdmin(admin);
        eventTest1.setParticipants(participantList);
        eventTest1.setRules(rules);
        eventTest1.setTime(time);


        final long eventId2 = 342;
        final String admin2 = "ezi.boi";
        final List<Participant> participantList2 = new ArrayList<>();
        final Participant participant12 = new Participant();
        participant12.setNetId("P2");
        participantList.add(participant12);
        final Rule rule12 = Rule.MALE_ONLY;
        final List<Rule> rules2 = new ArrayList<>();
        rules.add(rule12);

        final Event eventTest2 = new Event();
        eventTest2.setEventId(eventId2);
        eventTest2.setEventType(EventType.COMPETITION);
        eventTest2.setAdmin(admin2);
        eventTest2.setParticipants(participantList2);
        eventTest2.setRules(rules2);
        eventTest2.setTime(LocalDateTime.now());

        final long eventId3 = 34332;
        final String admin3 = "ezi.boiul";
        final List<Participant> participantList3 = new ArrayList<>();
        final Participant participant13 = new Participant();
        participant13.setNetId("P3");
        participantList.add(participant13);
        participantList.add(participant12);
        final Rule rule13 = Rule.MALE_ONLY;
        final List<Rule> rules3 = new ArrayList<>();
        rules.add(rule13);

        final Event eventTest3 = new Event();
        eventTest3.setEventId(eventId3);
        eventTest3.setEventType(EventType.COMPETITION);
        eventTest3.setAdmin(admin3);
        eventTest3.setParticipants(participantList3);
        eventTest3.setRules(rules3);
        eventTest3.setTime(LocalDateTime.now());

        when(eventRepository.getEventsByParticipant(participant12.getNetId())).thenReturn(
            Arrays.asList(eventTest2, eventTest3)
        );

        List<Event> res = eventService.getEventsByParticipant(participant12.getNetId());
        assertEquals(2, res.size());
        assertEquals(eventTest2, res.get(0));
        assertEquals(eventTest3, res.get(1));
    }

    @Test
    void deleteEventTest() {
        final long eventId = 344322;
        final EventType eventType = EventType.COMPETITION;
        final String admin = "ezi.boi";
        final LocalDateTime time = LocalDateTime.now();
        List<Participant> participantList = new ArrayList<>();
        Participant participant1 = new Participant();
        participant1.setNetId("P1");
        participantList.add(participant1);
        final Rule rule1 = Rule.MINIMUM_C4;
        final Rule rule2 = Rule.ONLY_PROFESSIONAL;
        List<Rule> rules = new ArrayList<>();
        rules.add(rule1);
        rules.add(rule2);

        Event eventTest1 = new Event();
        eventTest1.setEventId(eventId);
        eventTest1.setEventType(eventType);
        eventTest1.setAdmin(admin);
        eventTest1.setParticipants(participantList);
        eventTest1.setRules(rules);
        eventTest1.setTime(time);

        doNothing().when(eventRepository).deleteById(eventTest1.getEventId());
        eventService.deleteEvent(eventTest1.getEventId());
        verify(eventRepository, times(1)).deleteById(eventTest1.getEventId());
    }

    @Test
    void checkTimeConstraintsTest() {
        final long eventId = 1;
        final EventType eventType = EventType.TRAINING;
        final String admin = "ezi.boiul";
        final LocalDateTime time = LocalDateTime.now();
        List<Participant> participantList = new ArrayList<>();
        Participant participant1 = new Participant();
        participant1.setNetId("P1");
        participantList.add(participant1);
        final Rule rule1 = Rule.MINIMUM_C4;
        final Rule rule2 = Rule.ONLY_PROFESSIONAL;
        List<Rule> rules = new ArrayList<>();
        rules.add(rule1);
        rules.add(rule2);

        Event eventTestTrainingGood = new Event();
        eventTestTrainingGood.setEventId(eventId);
        eventTestTrainingGood.setEventType(eventType);
        eventTestTrainingGood.setAdmin(admin);
        eventTestTrainingGood.setParticipants(participantList);
        eventTestTrainingGood.setRules(rules);
        eventTestTrainingGood.setTime(time.plusDays(100));

        Event eventTestTrainingBad = new Event();
        eventTestTrainingBad.setEventId(2);
        eventTestTrainingBad.setEventType(eventType);
        eventTestTrainingBad.setAdmin(admin);
        eventTestTrainingBad.setParticipants(participantList);
        eventTestTrainingBad.setRules(rules);
        eventTestTrainingBad.setTime(time.plusSeconds(10));

        EventType eventTypeComp = EventType.COMPETITION;

        Event eventTestCompetitionGood = new Event();
        eventTestCompetitionGood.setEventId(eventId);
        eventTestCompetitionGood.setEventType(eventTypeComp);
        eventTestCompetitionGood.setAdmin(admin);
        eventTestCompetitionGood.setParticipants(participantList);
        eventTestCompetitionGood.setRules(rules);
        eventTestCompetitionGood.setTime(time.plusDays(100));

        Event eventTestCompetitionBad = new Event();
        eventTestCompetitionBad.setEventId(2);
        eventTestCompetitionBad.setEventType(eventType);
        eventTestCompetitionBad.setAdmin(admin);
        eventTestCompetitionBad.setParticipants(participantList);
        eventTestCompetitionBad.setRules(rules);
        eventTestCompetitionBad.setTime(time.plusSeconds(10));

        Set<Long> ids = new HashSet<>();
        ids.add(eventTestCompetitionBad.getEventId());
        ids.add(eventTestCompetitionGood.getEventId());
        ids.add(eventTestTrainingBad.getEventId());
        ids.add(eventTestTrainingGood.getEventId());

        List<Event> filteredEvents = new ArrayList<>();
        filteredEvents.add(eventTestCompetitionGood);
        filteredEvents.add(eventTestTrainingGood);

        List<Event> allEvents = new ArrayList<>();
        allEvents.add(eventTestCompetitionBad);
        allEvents.add(eventTestCompetitionGood);
        allEvents.add(eventTestTrainingBad);
        allEvents.add(eventTestTrainingGood);
        when(eventRepository.findAll()).thenReturn(allEvents);
        List<Event> res = eventService.getMatchingEvents(ids);
        assertEquals(filteredEvents, res);
    }

}