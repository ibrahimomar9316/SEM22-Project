package event.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import event.domain.enums.Position;
import org.junit.jupiter.api.Test;


class EventJoinModelTest {

    @Test
    void testConstructorAndGetters() {
        Position position = Position.COACH;
        long eventId = 1L;
        EventJoinModel eventJoinModel = new EventJoinModel(position, eventId);

        assertEquals(position, eventJoinModel.getPosition());
        assertEquals(eventId, eventJoinModel.getEventId());

        eventJoinModel.setEventId(2L);
        eventJoinModel.setPosition(Position.COX);

        assertNotEquals(eventId, eventJoinModel.getEventId());
        assertNotEquals(position, eventJoinModel.getPosition());
    }
}
