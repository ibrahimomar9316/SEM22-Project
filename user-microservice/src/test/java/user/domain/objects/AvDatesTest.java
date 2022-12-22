package user.domain.objects;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AvDatesTest {

    @Test
    void testEquals() {

        LocalDateTime t1 = LocalDateTime.now();
        LocalDateTime t2 = LocalDateTime.now().plusDays(1);
        AvDates av1 = new AvDates(t1, t2);
        AvDates av2 = new AvDates(t1, t2);
        AvDates av3 = new AvDates(t1, t1);
        AvDates av4 = new AvDates(t2, t2);
        AvDates av = new AvDates();

        assertThat(av1).isEqualTo(av1);
        assertThat(av1).isEqualTo(av2);
        assertThat(av1).isNotEqualTo(av3);
        assertThat(av1).isNotEqualTo(av4);
        assertThat(av1).isNotEqualTo(av);
        assertThat(av1).isNotEqualTo(null);
        assertThat(av1).isNotEqualTo(1);
    }

    @Test
    void testHashCode() {
        LocalDateTime t1 = LocalDateTime.now();
        LocalDateTime t2 = LocalDateTime.now().plusDays(1);
        AvDates av1 = new AvDates(t1, t2);
        AvDates av2 = new AvDates(t1, t2);
        AvDates av3 = new AvDates(t1, t1);

        assertThat(av1.hashCode()).isEqualTo(av1.hashCode());
        assertThat(av1.hashCode()).isEqualTo(av2.hashCode());
        assertThat(av1.hashCode()).isNotEqualTo(av3.hashCode());
    }

    @Test
    void testToString() {
        LocalDateTime t1 = LocalDateTime.now();
        LocalDateTime t2 = LocalDateTime.now().plusDays(1);
        AvDates av1 = new AvDates(t1, t2);

        String result = av1.toString();
        assertThat(result.contains("AvailableDates")).isTrue();
        assertThat(result.contains(" from: ")).isTrue();
        assertThat(result.contains(", to: ")).isTrue();
        assertThat(result.contains("}")).isTrue();
    }
}