package user.controllers;

import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<AppUser> updateUser(@RequestBody UserDetailsModel request) throws Exception {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/edit").toUriString());

        AppUser currentUser = new AppUser(auth.getNetId());
        //TODO Update user in DB
        String netId = currentUser.getNetId();
        boolean isMale = request.getGender() == Gender.MALE;
        boolean isCompetitive = request.getCompetitive().equals("PROFESSIONAL");
        String position = request.getPrefPosition().toString();
        String certificate = request.getCertificate().toString();

        UserCertificateDto ucd = new UserCertificateDto(netId, isMale, isCompetitive, position, certificate);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String json = new ObjectMapper().writeValueAsString(ucd);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        ResponseEntity<UserCertificateDto> obj = new RestTemplate()
            .postForEntity("http://localhost:8084/api/certificate/filter", entity, UserCertificateDto.class);
        if (obj.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.created(uri).body(currentUser);
        } else {
            throw new NotFoundException("Incorrectly saved in user microservice");
        }

    }
}
