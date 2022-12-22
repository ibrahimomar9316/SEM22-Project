package user.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.List;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import user.authentication.AuthManager;
import user.datatransferobjects.UserCertificateDto;
import user.datatransferobjects.UserDto;
import user.domain.entities.AppUser;
import user.domain.enums.Gender;
import user.models.UserDetailsModel;
import user.service.UserService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SuppressWarnings("PMD")
public class UserController {

    private final UserService appUserService;

    // This is used and initiated to check if the token is
    // valid and to get the netID
    private final transient AuthManager auth;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

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
     * @param token  theToken
     * @return  the saved user in the form of a DTO
     */
    @PostMapping("/user/save")
    public ResponseEntity<String> saveUser(@RequestHeader("Authorization") String token) {
        AppUser savedUser = new AppUser(auth.getNetId());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        appUserService.saveAppUser(savedUser);
        String response = auth.getNetId();
        return ResponseEntity.created(uri).body(response);
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
        currentUser.setAvDates(request.getAvDates());
        appUserService.updateUser(currentUser);

        UserCertificateDto ucd = new UserCertificateDto(
            currentUser.getGender() == Gender.MALE,
                currentUser.isCompetitive(),
                currentUser.getPrefPosition().toString(),
                currentUser.getCertificate().toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token.split(" ")[1]);
        String json = new ObjectMapper().writeValueAsString(ucd);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        ResponseEntity<Integer> hashedIndex = new RestTemplate()
            .postForEntity("http://localhost:8084/api/certificate/filter", entity, Integer.class);
        if (hashedIndex.getBody() == 404) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error in generating index!");
        }
        if (hashedIndex.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Successfully updated user:\n" + currentUser
                            + ", hashedIndex: " + hashedIndex.getBody());
        } else {
            throw new NotFoundException("Incorrectly saved in user microservice");
        }

    }
}
