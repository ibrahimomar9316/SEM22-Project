package event.domain.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import event.domain.enums.Position;
import org.junit.jupiter.api.Test;

class ParticipantTest {

    @Test
    public void testGetPosition() {
        Participant participant = new Participant(Position.PORT_SIDE_ROWER, "netId");
        assertEquals(Position.PORT_SIDE_ROWER, participant.getPosition());
    }

    @Test
    public void testGetNetId() {
        Participant participant = new Participant(Position.PORT_SIDE_ROWER, "netId");
        assertEquals("netId", participant.getNetId());
    }

    @Test
    public void testSetPosition() {
        Participant participant = new Participant(Position.PORT_SIDE_ROWER, "netId");
        participant.setPosition(Position.COACH);
        assertEquals(Position.COACH, participant.getPosition());
    }

    @Test
    public void testSetNetId() {
        Participant participant = new Participant(Position.PORT_SIDE_ROWER, "netId");
        participant.setNetId("newNetId");
        assertEquals("newNetId", participant.getNetId());
    }

    @Test
    public void testEquals() {
        Participant participant1 = new Participant(Position.PORT_SIDE_ROWER, "netId");
        Participant participant2 = new Participant(Position.PORT_SIDE_ROWER, "netId");
        assertEquals(participant1, participant2);
        participant1.setNetId("newNetId");
        assertNotEquals(participant1, participant2);
    }

    @Test
    public void testHashCode() {
        Participant participant1 = new Participant(Position.PORT_SIDE_ROWER, "netId");
        Participant participant2 = new Participant(Position.PORT_SIDE_ROWER, "netId");
        assertEquals(participant1.hashCode(), participant2.hashCode());

        participant1.setNetId("newNetId");
        assertNotEquals(participant1.hashCode(), participant2.hashCode());
    }

    @Test
    public void testToString() {
        Participant participant = new Participant(Position.PORT_SIDE_ROWER, "netId");
        String expectedString = "PORT_SIDE_ROWER: netId";
        assertEquals(expectedString, participant.toString());
    }

}
