package certificate.controllers;

import certificate.authentication.AuthManager;
import certificate.datatransferobjects.UserCertificateDto;
import certificate.domain.entities.Certificate;
import certificate.service.CertificateService;
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

    private transient CertificateService certificateService;

    // This is used and initiated to check if the token is
    // valid and to get the netID
    private final transient AuthManager auth;

    /**
     * Instantiates a new Certificate controller.
     *
     * @param certificateService the certificate service
     * @param auth               the auth
     */
    @Autowired
    public CertificateController(CertificateService certificateService, AuthManager auth) {
        this.certificateService = certificateService;
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
        int id = certificateService.generateId(ucd.isMale(),
                ucd.isCompetitive(), ucd.getPosition(), ucd.getCertificate());
        Certificate certificate = new Certificate(auth.getNetId(), id);
        certificateService.saveCertificate(certificate);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }
}
