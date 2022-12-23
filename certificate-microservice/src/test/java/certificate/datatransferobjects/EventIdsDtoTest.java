package certificate.datatransferobjects;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class EventIdsDtoTest {

    @Test
    void getIds() {
        List<Long> numbers = List.of(1L, 2L, 3L);
        EventIdsDto dto = new EventIdsDto(numbers);
        assertThat(dto.getIds()).isEqualTo(numbers);
    }

    @Test
    void setIds() {
        EventIdsDto dto = new EventIdsDto(new ArrayList<>());
        assertThat(dto.getIds()).isEqualTo(new ArrayList<>());

        List<Long> numbers = List.of(1L, 2L, 3L);
        dto.setIds(numbers);
        assertThat(dto.getIds()).isEqualTo(numbers);
    }

    @Test
    void testEquals() {
        EventIdsDto dto1 = new EventIdsDto(List.of(1L));
        EventIdsDto dto2 = new EventIdsDto(List.of(1L));
        EventIdsDto dto3 = new EventIdsDto(List.of(2L));

        assertThat(dto1).isEqualTo(dto1);
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1).isNotEqualTo(dto3);
    }

}