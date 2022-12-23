package authentication.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

class PasswordHashingServiceTest {

    @Test
    void hash() {
        PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
        PasswordHashingService service = new PasswordHashingService(encoder);

        when(encoder.encode("kek")).thenReturn("wow");

        assertEquals(service.hash(new Password("kek")), new HashedPassword("wow"));
    }
}