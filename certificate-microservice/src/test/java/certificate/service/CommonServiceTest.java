package certificate.service;

import static org.assertj.core.api.Assertions.assertThat;

import certificate.domain.enums.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class CommonServiceTest {

    @Autowired
    private CommonService commonService;

    @Test
    void generateId() {
        assertThat(commonService.generateId(Gender.MALE, true, "COX", "C4"))
                .isEqualTo(111);
        assertThat(commonService.generateId(Gender.MALE, true, "COX", "FOURPLUS"))
                .isEqualTo(112);
        assertThat(commonService.generateId(Gender.MALE, true, "COX", "EIGHTPLUS"))
                .isEqualTo(113);
        assertThat(commonService.generateId(Gender.MALE, true, "COX", "NONE"))
                .isEqualTo(110);

        assertThat(commonService.generateId(Gender.MALE, true, "COACH", "NONE"))
                .isEqualTo(110);
        assertThat(commonService.generateId(Gender.FEMALE, true, "COACH", "NONE"))
                .isEqualTo(210);
        assertThat(commonService.generateId(Gender.NONE, true, "COACH", "NONE"))
                .isEqualTo(10);
        assertThat(commonService.generateId(Gender.MALE, false, "COACH", "NONE"))
                .isEqualTo(100);

    }
}