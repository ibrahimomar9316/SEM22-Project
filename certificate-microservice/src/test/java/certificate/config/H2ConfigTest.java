package certificate.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

public class H2ConfigTest {

    private final H2Config h2Config = Mockito.mock(H2Config.class);

    @Test
    public void testDataSource() {
        when(h2Config.dataSource()).thenReturn(Mockito.mock(DataSource.class));
        DataSource dataSource = h2Config.dataSource();
        assertNotNull(dataSource);
    }

    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    @Test
    public void testEnvironment() {
        when(h2Config.getEnvironment()).thenReturn(Mockito.mock(Environment.class));
        String actualDriverClassName = h2Config.getEnvironment().getProperty("jdbc.driverClassName");
        assertEquals(driverClassName, actualDriverClassName);
    }
}




