package certificate.controllers;

import certificate.authentication.AuthManager;
import certificate.datatransferobjects.RuleDto;
import certificate.datatransferobjects.UserCertificateDto;
import certificate.domain.entities.Certificate;
import certificate.domain.entities.Rule;
import certificate.service.CertificateRulesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * The type Certificate controller.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CertificateController {
    @Autowired
    private RestTemplate restTemplate;

    private transient CertificateRulesService certificateRulesService;

    // This is used and initiated to check if the token is
    // valid and to get the netID
    private final transient AuthManager auth;

    /**
     * Instantiates a new Certificate controller.
     *
     * @param certificateRulesService the certificate service
     * @param auth               the auth
     */
    @Autowired
    public CertificateController(CertificateRulesService certificateRulesService, AuthManager auth) {
        this.certificateRulesService = certificateRulesService;
        this.auth = auth;
    }

    /**
     * Gets rest template.
     *
     * @return the rest template
     */
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    /**
     * Filter response entity.
     *
     * @param ucd the ucd
     * @return the response entity
     */
    @PostMapping("/certificate/filter")
    public ResponseEntity<Integer> filter(@RequestBody UserCertificateDto ucd) {
        int id = certificateRulesService.generateId(ucd.isMale(),
                ucd.isCompetitive(),
                ucd.getPosition(),
                ucd.getCertificate());
        try {
            certificateRulesService.saveCertificate(new Certificate(auth.getNetId(), id));
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(404);
        }
    }

    /**
     * Gets rule index.
     *
     * @param rules the rules
     * @return the rule index
     */
    @PostMapping("/certificate/getRuleIndex")
    public ResponseEntity<Integer> getRuleIndex(@RequestBody RuleDto rules) {
        int id = certificateRulesService.generateId(rules.isGendered(),
                rules.isPro(),
                "COX",
                rules.getCertificate());
        try {
            certificateRulesService.saveRule(new Rule(rules.getEventId(), id));
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(404);
        }
    }
}
