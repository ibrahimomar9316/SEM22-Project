package certificate.service;

import static org.assertj.core.api.Assertions.assertThat;

import certificate.domain.entities.Certificate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CertificateServiceTest {

    @Autowired
    private CertificateService certificateService;

    @Test
    void save() {
        List<Certificate> empty = certificateService.getAllCertificates();
        assertThat(empty.size()).isEqualTo(0);

        Certificate c = new Certificate("user", 2222);
        certificateService.save(c);
        List<Certificate> list = certificateService.getAllCertificates();

        assertThat(list.size()).isEqualTo(1);
        assertThat(list.contains(c)).isTrue();
    }

    @Test
    void getCertificateBy() {
        Certificate c1 = new Certificate("user", 212302);
        Certificate c2 = new Certificate("none", 23222);
        certificateService.save(c1);
        certificateService.save(c2);

        Certificate found = certificateService.getCertificateBy("user");
        assertThat(found).isEqualTo(c1);
    }
}