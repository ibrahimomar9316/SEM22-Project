package event.datatransferobjects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class EventIdsDtoTest {

    @Test
    public void testGetIds() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        EventIdsDto eventIdsDto = new EventIdsDto(ids);
        assertEquals(ids, eventIdsDto.getIds());
    }

    @Test
    public void testSetIds() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        EventIdsDto eventIdsDto = new EventIdsDto();
        eventIdsDto.setIds(ids);
        assertEquals(ids, eventIdsDto.getIds());
    }

    @Test
    public void testEquals() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        EventIdsDto eventIdsDto1 = new EventIdsDto(ids);
        EventIdsDto eventIdsDto2 = new EventIdsDto(ids);
        assertEquals(eventIdsDto1, eventIdsDto2);

        eventIdsDto1.setIds(Arrays.asList(4L, 5L));
        assertNotEquals(eventIdsDto1, eventIdsDto2);
    }

    @Test
    public void testHashCode() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        EventIdsDto eventIdsDto1 = new EventIdsDto(ids);
        EventIdsDto eventIdsDto2 = new EventIdsDto(ids);
        assertEquals(eventIdsDto1.hashCode(), eventIdsDto2.hashCode());

        eventIdsDto1.setIds(Arrays.asList(4L, 5L));
        assertNotEquals(eventIdsDto1.hashCode(), eventIdsDto2.hashCode());
    }
}

