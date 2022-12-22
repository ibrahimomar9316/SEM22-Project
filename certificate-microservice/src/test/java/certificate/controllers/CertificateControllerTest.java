package certificate.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import certificate.authentication.AuthManager;
import certificate.datatransferobjects.EventIdsDto;
import certificate.datatransferobjects.RuleDto;
import certificate.datatransferobjects.UserCertificateDto;
import certificate.domain.entities.Certificate;
import certificate.domain.entities.Rule;
import certificate.domain.enums.Gender;
import certificate.service.CertificateService;
import certificate.service.CommonService;
import certificate.service.RuleService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class CertificateControllerTest {

    @Autowired
    private CertificateController certificateController;

    @MockBean
    private CertificateService certificateService;

    @MockBean
    private RuleService ruleService;

    @MockBean
    private CommonService commonService;

    @MockBean
    private AuthManager auth;


    @Test
    void filter() {
        when(commonService.generateId(Gender.MALE, true, "COX", "C4"))
                .thenReturn(111);
        when(auth.getNetId()).thenReturn("user");
        UserCertificateDto dto = new UserCertificateDto(Gender.MALE, true, "COX", "C4");

        ResponseEntity<Integer> res = certificateController.filter(dto);
        assertThat(res.getStatusCodeValue()).isEqualTo(200);
        assertThat(res.getBody()).isEqualTo(111);

        Certificate c = new Certificate("user", 111);
        verify(certificateService, atMostOnce()).save(c);

        doThrow(IllegalArgumentException.class).when(certificateService).save(any(Certificate.class));

        ResponseEntity<Integer> res2 = certificateController.filter(dto);
        assertThat(res2.getStatusCodeValue()).isEqualTo(400);
        assertThat(res2.getBody()).isEqualTo(404);
    }

    @Test
    void getRuleIndex() {
        when(commonService.generateId(Gender.MALE, true, "COX", "C4"))
                .thenReturn(111);
        RuleDto dto = new RuleDto(1L, Gender.MALE, true, "C4");

        ResponseEntity<Integer> res = certificateController.getRuleIndex(dto);
        assertThat(res.getStatusCodeValue()).isEqualTo(200);
        assertThat(res.getBody()).isEqualTo(111);

        Rule r = new Rule(1L, 111);
        verify(ruleService, atMostOnce()).save(r);

        doThrow(IllegalArgumentException.class).when(ruleService).save(any(Rule.class));

        ResponseEntity<Integer> res2 = certificateController.getRuleIndex(dto);
        assertThat(res2.getStatusCodeValue()).isEqualTo(400);
        assertThat(res2.getBody()).isEqualTo(404);
    }

    @Test
    void getAllCertificates() {
        when(certificateService.getAllCertificates()).thenReturn(new ArrayList<>());

        ResponseEntity<String> res = certificateController.getAllCertificates();
        assertThat(res.getStatusCodeValue()).isEqualTo(400);
        assertThat(res.getBody()).isEqualTo("No user has put their preferences!");

        List<Certificate> list = List.of(new Certificate("kek", 123));
        when(certificateService.getAllCertificates()).thenReturn(list);

        ResponseEntity<String> res2 = certificateController.getAllCertificates();
        assertThat(res2.getStatusCodeValue()).isEqualTo(200);
        assertThat(res2.getBody()).isEqualTo(list.toString());
    }

    @Test
    void getAllRules() {
        when(ruleService.getAllCertificates()).thenReturn(new ArrayList<>());

        ResponseEntity<String> res = certificateController.getAllRules();
        assertThat(res.getStatusCodeValue()).isEqualTo(400);
        assertThat(res.getBody()).isEqualTo("No events have put their rules!");

        List<Rule> list = List.of(new Rule(2L, 231));
        when(ruleService.getAllCertificates()).thenReturn(list);

        ResponseEntity<String> res2 = certificateController.getAllRules();
        assertThat(res2.getStatusCodeValue()).isEqualTo(200);
        assertThat(res2.getBody()).isEqualTo(list.toString());
    }

    @Test
    void getAllMatchingRules() {
        when(ruleService.getAllCertificates()).thenReturn(new ArrayList<>());

        ResponseEntity<EventIdsDto> res = certificateController.getAllMatchingRules();
        assertThat(res.getStatusCodeValue()).isEqualTo(400);
        assertThat(res.getBody()).isEqualTo(new EventIdsDto(new ArrayList<>()));

        List<Rule> list = List.of(new Rule(2L, 231),
                new Rule(3L, 111),
                new Rule(4L, 123),
                new Rule(5L, 200));
        when(ruleService.getAllCertificates()).thenReturn(list);
        when(auth.getNetId()).thenReturn("user");

        Certificate c = new Certificate("user", 111);
        when(certificateService.getCertificateBy("user")).thenReturn(c);

        List<Long> ids = List.of(3L);
        when(ruleService.getAllMatching(c)).thenReturn(ids);

        ResponseEntity<EventIdsDto> res2 = certificateController.getAllMatchingRules();
        assertThat(res2.getStatusCodeValue()).isEqualTo(200);
        assertThat(res2.getBody()).isEqualTo(new EventIdsDto(ids));
    }
}