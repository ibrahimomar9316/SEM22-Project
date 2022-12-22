package certificate.controllers;


import certificate.authentication.AuthManager;
import certificate.datatransferobjects.EventIdsDto;
import certificate.datatransferobjects.RuleDto;
import certificate.datatransferobjects.UserCertificateDto;
import certificate.domain.entities.Certificate;
import certificate.domain.entities.Rule;
import certificate.service.CertificateService;
import certificate.service.CommonService;
import certificate.service.RuleService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Controller for the certificate-microservice to manage the API endpoints.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CertificateController {
    @Autowired
    private RestTemplate restTemplate;

    private transient CertificateService certificateService;
    private transient RuleService ruleService;
    private transient CommonService common;

    // This is used and initiated to check if the token is
    // valid and to get the netID
    private final transient AuthManager auth;

    /**
     * Instantiates a new certificate controller using the services to process data in the databases and
     * to hash the indexes.
     *
     * @param certificateService service used to operate on the database containing the hashed preferences for users
     * @param ruleService service used to operate on the database containing the hashed rules for events
     * @param common common service used to hash the values for the users and events
     * @param auth the authentication manager that is used to automatically manage the Spring Security
     */
    @Autowired
    public CertificateController(CertificateService certificateService,
                                 RuleService ruleService,
                                 CommonService common,
                                 AuthManager auth) {
        this.certificateService = certificateService;
        this.ruleService = ruleService;
        this.common = common;
        this.auth = auth;
    }

    /**
     * Getter function for the rest template used.
     *
     * @return a RestTemplate object representing the rest template.
     */
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    /**
     * API endpoint used to hash a user preferences, storing them afterwards in the database.
     *
     * @param ucd the dataTransferObject received from the user-microservice containing all the data to be hashed
     * @return the response entity that we get after submitting the request
     */
    @PostMapping("/certificate/filter")
    public ResponseEntity<Integer> filter(@RequestBody UserCertificateDto ucd) {
        int id = common.generateId(ucd.getGender(),
                ucd.isCompetitive(),
                ucd.getPosition(),
                ucd.getCertificate());
        try {
            certificateService.save(new Certificate(auth.getNetId(), id));
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(404);
        }
    }

    /**
     * API endpoint used to hash an event rules, storing them afterwards in the database.
     *
     * @param rules the dataTransferObject received from the event-microservice containing all the data to be hashed
     * @return the response entity that we get after submitting the request
     */
    @PostMapping("/certificate/getRuleIndex")
    public ResponseEntity<Integer> getRuleIndex(@RequestBody RuleDto rules) {
        int id = common.generateId(rules.getGenderConstraint(),
                rules.isPro(),
                "COX",
                rules.getCertificate());
        try {
            ruleService.save(new Rule(rules.getEventId(), id));
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(404);
        }
    }

    /**
     * API endpoint used to get all users hashed preferences from the database.
     *
     * @return a string containing all the users with their hashed preferences
     */
    @GetMapping({"/certificate/getAllUsersHash"})
    public ResponseEntity<String> getAllCertificates() {
        // checks if the database is not empty
        if (certificateService.getAllCertificates().size() != 0) {
            return ResponseEntity.ok(certificateService.getAllCertificates().toString());
        } else {
            // else returns BAD_REQUEST
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("No user has put their preferences!");
        }
    }

    /**
     * API endpoint used to get all events hashed rules from the database.
     *
     * @return a string containing all the events with their hashed rules
     */
    @GetMapping({"/certificate/getAllRulesHash"})
    public ResponseEntity<String> getAllRules() {
        // checks if the database is not empty
        if (ruleService.getAllCertificates().size() != 0) {
            return ResponseEntity.ok(ruleService.getAllCertificates().toString());
        } else {
            // else returns BAD_REQUEST
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("No events has put their rules!");
        }
    }

    /**
     * API endpoint used to get all ids of matching events from hashed rules from the database.
     *
     * @return and Dto object which contains list of all event ids which match user qualifications/preferences
     */
    @GetMapping({"/certificate/getAllMatchingRules"})
    public ResponseEntity<EventIdsDto> getAllMatchingRules() {
        // checks if the database is not empty
        if (ruleService.getAllCertificates().size() != 0) {
            String userNetId = auth.getNetId().toString();
            Certificate certificate = certificateService.getCertificateBy(userNetId);
            List<Long> eventIds = ruleService.getAllMatching(certificate);
            EventIdsDto eventIdsDto = new EventIdsDto(eventIds);
            return ResponseEntity.status(HttpStatus.OK).body(eventIdsDto);
        } else {
            // else returns BAD_REQUEST
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new EventIdsDto(new ArrayList<>()));
        }
    }

}
