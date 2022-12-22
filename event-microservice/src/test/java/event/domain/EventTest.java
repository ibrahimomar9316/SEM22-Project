package event.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import event.domain.entities.Event;
import event.domain.enums.EventType;
import event.domain.enums.Position;
import event.domain.objects.Participant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class EventTest {

    @Test
    public void testNumberOfParticipants() {
        Event event = new Event(EventType.COMPETITION, "admin", LocalDateTime.now(),
                Arrays.asList(new Participant(Position.COACH), new Participant(Position.COX)));
        assertEquals(2, event.numberOfParticipants());
    }

    @Test
    public void testToStringNewEvent() {
        Event event = new Event(EventType.TRAINING, "admin", LocalDateTime.now(),
                Arrays.asList(new Participant(Position.COACH)));
        String expectedString = "You created a new event! \nID: " + event.getEventId()
                + "\nAdministrator: " + event.getAdmin()
                + "\nNumber of participants: " + event.numberOfParticipants();
        assertEquals(expectedString, event.toStringNewEvent());
    }

    @Test
    public void testToString() {
        Event event = new Event(EventType.TRAINING, "admin", LocalDateTime.now(),
                Arrays.asList(new Participant(Position.COACH)));
        String expectedString = "Training made by admin, event ID: " + event.getEventId() + ", time "
                + event.getTime() + "\n" + event.getParticipants() + "\n";
        assertEquals(expectedString, event.toString());
    }

    @Test
    public void testToStringJoin() {
        Event event = new Event(EventType.COMPETITION, "admin", LocalDateTime.now(),
                Arrays.asList(new Participant(Position.COACH)));
        String expectedString = "You have joined event " + event.getEventId() + " made by "
                + event.getAdmin();
        assertEquals(expectedString, event.toStringJoin());
    }

    @Test
    public void testToStringUpdate() {
        Event event = new Event(EventType.TRAINING, "admin", LocalDateTime.now(),
                Arrays.asList(new Participant(Position.COACH)));
        String expectedString = "You have updated event " + event.getEventId();
        assertEquals(expectedString, event.toStringUpdate());
    }
    @Test
    public void testGetEventId() {
        Event event = new Event(EventType.TRAINING, "admin", LocalDateTime.now(),
                Arrays.asList(new Participant(Position.COACH)));
        event.setEventId(123);
        assertEquals(123, event.getEventId());
    }

    @Test
    public void testGetEventType() {
        Event event = new Event(EventType.TRAINING, "admin", LocalDateTime.now(),
                Arrays.asList(new Participant(Position.COACH)));
        assertEquals(EventType.TRAINING, event.getEventType());
    }

    @Test
    public void testGetAdmin() {
        Event event = new Event(EventType.TRAINING, "admin", LocalDateTime.now(),
                Arrays.asList(new Participant(Position.COACH)));
        assertEquals("admin", event.getAdmin());
    }

    @Test
    public void testGetTime() {
        LocalDateTime time = LocalDateTime.now();
        Event event = new Event(EventType.TRAINING, "admin", time,
                Arrays.asList(new Participant(Position.COACH)));
        assertEquals(time, event.getTime());
    }

    @Test
    public void testGetParticipants() {
        List<Participant> participants = Arrays.asList(new Participant(Position.COACH),
                new Participant(Position.COX));
        Event event = new Event(EventType.TRAINING, "admin", LocalDateTime.now(), participants);
        assertEquals(participants, event.getParticipants());
    }

    @Test
    public void testSetEventId() {
        Event event = new Event(EventType.TRAINING, "admin", LocalDateTime.now(),
                Arrays.asList(new Participant(Position.COACH)));
        event.setEventId(123);
        assertEquals(123, event.getEventId());
    }

    @Test
    public void testSetEventType() {
        Event event = new Event(EventType.TRAINING, "admin", LocalDateTime.now(),
                Arrays.asList(new Participant(Position.COACH)));
        event.setEventType(EventType.COMPETITION);
        assertEquals(EventType.COMPETITION, event.getEventType());
    }

    @Test
    public void testSetAdmin() {
        Event event = new Event(EventType.TRAINING, "admin", LocalDateTime.now(),
                Arrays.asList(new Participant(Position.COACH)));
        event.setAdmin("newAdmin");
        assertEquals("newAdmin", event.getAdmin());
    }

    @Test
    public void testSetTime() {
        LocalDateTime time = LocalDateTime.now();
        Event event = new Event(EventType.TRAINING, "admin", time,
                Arrays.asList(new Participant(Position.COACH)));
        LocalDateTime newTime = time.plusDays(1);
        event.setTime(newTime);
        assertEquals(newTime, event.getTime());
    }
    @Test
    public void testSetParticipants() {
        List<Participant> participants = Arrays.asList(new Participant(Position.COACH),
                new Participant(Position.COX));
        Event event = new Event(EventType.TRAINING, "admin", LocalDateTime.now(), participants);
        List<Participant> newParticipants = Arrays.asList(new Participant(Position.PORT_SIDE_ROWER),
                new Participant(Position.SCULLING_ROWER));
        event.setParticipants(newParticipants);
        assertEquals(newParticipants, event.getParticipants());
    }
}

