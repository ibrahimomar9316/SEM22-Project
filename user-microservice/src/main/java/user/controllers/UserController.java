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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import user.authentication.AuthManager;
import user.authentication.JwtRequestFilter;
import user.datatransferobjects.UserCertificateDto;
import user.datatransferobjects.UserDto;
import user.domain.entities.AppUser;
import user.domain.enums.Gender;
import user.models.AuthTokenHolder;
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
     * Endpoint saving a user in the database.
     *
     * @param gotUser - user body to be saved
     * @return - saved user
     */
    @PostMapping("/user/save")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto gotUser) throws NotFoundException {
        AppUser savedUser = new AppUser(gotUser.getUsername());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        appUserService.saveAppUser(savedUser);
        return ResponseEntity.created(uri).body(gotUser);
    }

    /**
     * Endpoint for updating fields such as gender, boatPreferences
     * and certification list of the given using. The method has restriction on changing user's netId
     * and password.
     * While the netId is unchangeable, the user can change his password in another endpoint.
     *
     * @param request new user body with updates
     * @return saved user
     */
    @PutMapping("/user/edit")
    public ResponseEntity<String> updateUser(@RequestHeader("Authorization") String token,
        @RequestBody UserDetailsModel request) throws Exception {
        AppUser currentUser = new AppUser(auth.getNetId());
        currentUser.setGender(request.getGender());
        currentUser.setPrefPosition(request.getPrefPosition());
        currentUser.setCertificate(request.getCertificate());
        currentUser.setCompetitive(request.isCompetitive());
        appUserService.updateUser(currentUser);

        UserCertificateDto ucd = new UserCertificateDto(
            currentUser.getGender()== Gender.MALE,
                currentUser.isCompetitive(),
                currentUser.getPrefPosition().toString(),
                currentUser.getCertificate().toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token.split(" ")[1]);
        String json = new ObjectMapper().writeValueAsString(ucd);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        ResponseEntity<UserCertificateDto> obj = new RestTemplate()
            .postForEntity("http://localhost:8084/api/certificate/filter", entity, UserCertificateDto.class);
        if (obj.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Successfully updated user:\n" + currentUser);
        } else {
            throw new NotFoundException("Incorrectly saved in user microservice");
        }

    }
}
