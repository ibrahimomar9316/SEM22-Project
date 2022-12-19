package certificate.controllers;

import certificate.authentication.AuthManager;
import certificate.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    public CertificateController(CertificateService certificateService, AuthManager auth) {
        this.certificateService = certificateService;
        this.auth = auth;
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @PostMapping("/certificate/filter")
    public void filter(@RequestBody String s) {
        System.out.println("BUNBUN");
        System.out.println(s);
    }
}
