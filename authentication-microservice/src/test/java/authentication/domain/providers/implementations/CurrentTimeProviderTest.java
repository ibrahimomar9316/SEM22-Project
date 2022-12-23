package authentication.domain.providers.implementations;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
class CurrentTimeProviderTest {

    @Autowired
    private CurrentTimeProvider currentTimeProvider;

    @Test
    void getCurrentTime() {
        Instant time1 = currentTimeProvider.getCurrentTime();
        Instant time2 = currentTimeProvider.getCurrentTime();
        assertThat(time1.isBefore(time2.plusSeconds(3600))).isTrue();
    }
}