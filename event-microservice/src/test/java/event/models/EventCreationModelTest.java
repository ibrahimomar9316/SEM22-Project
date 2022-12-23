package event.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import event.domain.enums.EventType;
import event.domain.enums.Position;
import event.domain.objects.Participant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class EventCreationModelTest {

    @Test
    void testConstructorAndGetters() {
        EventType eventType = EventType.TRAINING;
        LocalDateTime localDateTime = LocalDateTime.now();
        Participant participant1 = new Participant(Position.COACH);
        List<Participant> participants = new ArrayList<>();
        participants.add(participant1);
        EventCreationModel eventCreationModel = new EventCreationModel(eventType, localDateTime, participants);

        assertEquals(eventType, eventCreationModel.getEventType());
        assertEquals(localDateTime, eventCreationModel.getTime());
        assertEquals(participants, eventCreationModel.getParticipants());

        eventCreationModel.setEventType(EventType.COMPETITION);
        assertNotEquals(eventType, eventCreationModel.getEventType());

        eventCreationModel.setTime(LocalDateTime.now().minusDays(5));
        assertNotEquals(localDateTime, eventCreationModel.getTime());

        eventCreationModel.setParticipants(new ArrayList<>());
        assertNotEquals(participants, eventCreationModel.getParticipants());
    }
}
