package nl.tudelft.sem.template.user.api;

import java.net.URI;
import java.util.List;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import nl.tudelft.sem.template.user.datatransferobjects.UserDto;
import nl.tudelft.sem.template.user.domain.entities.AppUser;
import nl.tudelft.sem.template.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService appUserService;

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
     * @param appUser new user body with updates
     * @return saved user
     */
    @PutMapping("/user/update")
    public ResponseEntity<AppUser> updateUser(@RequestBody AppUser appUser) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/update").toUriString());
        return ResponseEntity.created(uri).body(appUserService.updateUser(appUser));
    }
}
