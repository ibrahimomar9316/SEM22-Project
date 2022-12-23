package user.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import user.authentication.AuthManager;
import user.datatransferobjects.AvailabilityDto;
import user.datatransferobjects.UserCertificateDto;
import user.domain.entities.AppUser;
import user.domain.entities.AppUserBuilder;
import user.domain.entities.Builder;
import user.domain.entities.Director;
import user.models.UserDetailsModel;
import user.service.UserService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService appUserService;

    // This is used and initiated to check if the token is
    // valid and to get the netID
    private final transient AuthManager auth;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    private RestTemplate restTemplate = new RestTemplate();

    private static final int BAD_REQUEST = 404;

    /**
     * Endpoint returning all users in the database.
     *
     * @return - List of all users in the database
     */
    @GetMapping("/user/all")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok().body(appUserService.getAppUsers());
    }

    /**
     * Endpoint used for saving a user in the database, endpoint that is called by the authentication microservice.
     *
     * @return  the saved user in the form of a DTO
     */
    @PostMapping("/user/save")
    public ResponseEntity<String> saveUser() {
        Builder builder = new AppUserBuilder();
        Director director = new Director();
        director.createAppUser(builder, auth.getNetId());
        AppUser savedUser = builder.build();
        appUserService.saveAppUser(savedUser);
        String response = auth.getNetId();
        return ResponseEntity.ok().body(response);
    }

    /**
     *Endpoint returning availability window of the user.
     *
     * @return AvailabilityDto containing dates
     */
    @GetMapping("/user/userAvailability")
    public ResponseEntity<AvailabilityDto> getUserAvailability() {
        return ResponseEntity.ok().body(appUserService.getUserAvailability());
    }

    /**
     * Endpoint for updating fields such as gender, boatPreferences
     * and certification list of the given using. The method has restriction on changing user's netId
     * and password.
     * While the netId is unchangeable, the user can change his password in another endpoint.
     * Also in this endpoint and api from the certificate microservice is called such that the preferences that
     * the user selects are updated and hashed into the certificate microservice using (api/certificate/filter).
     *
     * @param token header of the request that holds the token used for security
     * @param request body of the request holding the details to be updated for a user (stored as a dataTransferObject)
     * @return The saved and updated user
     */
    @PostMapping("/user/edit")
    public ResponseEntity<String> updateUser(@RequestHeader("Authorization") String token,
                                             @RequestBody UserDetailsModel request) throws Exception {
        AppUser currentUser = new AppUser(auth.getNetId());
        currentUser.setGender(request.getGender());
        currentUser.setPrefPosition(request.getPrefPosition());
        currentUser.setCertificate(request.getCertificate());
        currentUser.setCompetitive(request.isCompetitive());
        currentUser.setAvailableFrom(request.getAvailableFrom());
        currentUser.setAvailableTo(request.getAvailableTo());
        appUserService.updateUser(currentUser);

        UserCertificateDto ucd = new UserCertificateDto(
                currentUser.getGender(),
                currentUser.isCompetitive(),
                currentUser.getPrefPosition().toString(),
                currentUser.getCertificate().toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token.split(" ")[1]);
        String json = new ObjectMapper().writeValueAsString(ucd);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        ResponseEntity<Integer> hashedIndex = this.restTemplate
            .postForEntity("http://localhost:8084/api/certificate/filter", entity, Integer.class);
        if (hashedIndex.getBody() == BAD_REQUEST) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error in generating index!");
        }
        if (hashedIndex.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Successfully updated user:\n" + currentUser
                            + ", hashedIndex: " + hashedIndex.getBody());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Setter for the restTemplate.
     *
     * @param restTemplate The new restTemplate to set
     */
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
