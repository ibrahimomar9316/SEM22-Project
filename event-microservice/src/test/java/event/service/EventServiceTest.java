package event.service;

import event.authentication.AuthManager;
import event.domain.EventRepository;
import event.domain.entities.Event;
import event.domain.enums.EventType;
import event.domain.enums.Rule;
import event.domain.objects.Participant;
import javassist.NotFoundException;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
        EventType eventType = EventType.COMPETITION;
        String admin = "ezi.boi";
        LocalDateTime time = LocalDateTime.now();
        List<Participant> participantList = new ArrayList<>();
        Participant participant1 = new Participant();
        participantList.add(participant1);
        Rule rule1 = Rule.MALE_ONLY;
        List<Rule> rules = new ArrayList<>();
        rules.add(rule1);

        Event eventTest = new Event();
        eventTest.setEventId(eventId);
        eventTest.setEventType(eventType);
        eventTest.setAdmin(admin);
        eventTest.setParticipants(participantList);
        eventTest.setRules(rules);
        eventTest.setTime(time);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(eventTest));

        Event res = eventService.getEvent(eventId);
        assertEquals(res,eventTest);
    }
}