package nl.tudelft.sem.template.user.api;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import nl.tudelft.sem.template.user.api.forms.BoatToUserForm;
import nl.tudelft.sem.template.user.api.forms.CertificateToUserForm;
import nl.tudelft.sem.template.user.api.forms.RoleToUserForm;
import nl.tudelft.sem.template.user.dataTransferObjects.UserDTO;
import nl.tudelft.sem.template.user.domain.entities.AppUser;
import nl.tudelft.sem.template.user.domain.entities.Boat;
import nl.tudelft.sem.template.user.domain.entities.Certificate;
import nl.tudelft.sem.template.user.service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImplementation appUserService;

    static final String AUTH_URL_MS = "http://localhost:8081/";

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Endpoint returning all users in the database
     *
     * @return - List of all users in the database
     */
    @GetMapping("/user/all")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok().body(appUserService.getAppUsers());
    }

    /**
     * Endpoint saving a user in the database
     *
     * @param gotUser - user body to be saved
     * @return - saved user
     */
    @PostMapping("/user/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody UserDTO gotUser) throws NotFoundException {
        AppUser savedUser = new AppUser(gotUser.getUsername(), gotUser.getPassword());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(appUserService.saveAppUser(savedUser));
    }

    /**
     * Endpoint saving a boat in the database
     *
     * @param boat - boat body to be saved
     * @return - saved boat
     */
    @PostMapping("/boat/save")
    public ResponseEntity<Boat> saveBoat(@RequestBody Boat boat) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/boat/save").toUriString());
        return ResponseEntity.created(uri).body(appUserService.saveBoat(boat));
    }

    /**
     * Endpoint saving a certificate in the database
     *
     * @param certificate - certificate body to be saved
     * @return - saved certificate
     */
    @PostMapping("/certificate/save")
    public ResponseEntity<Certificate> saveBoat(@RequestBody Certificate certificate) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/certificate/save").toUriString());
        return ResponseEntity.created(uri).body(appUserService.saveCertificate(certificate));
    }

    /**
     * Endpoint adding boat to the specific user in the database
     *
     * @param boatToUserForm - form containing userId and boatId
     * @return - returns confirmation response
     * @throws NotFoundException - throws an exception when user with ID is not found
     */
    @PostMapping("/boat/add_to_user")
    public ResponseEntity<?> addBoatToAppUser(@RequestBody BoatToUserForm boatToUserForm) throws NotFoundException {
        appUserService.addBoatToAppUser(boatToUserForm.getAppUserId(), boatToUserForm.getBoatId());
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint adding certificate to the specific user in the database
     *
     * @param certificateToUserForm - form containing userId and boatId
     * @return - returns confirmation response
     * @throws NotFoundException - throws an exception when user with ID is not found
     */
    @PostMapping("/certificate/add_to_user")
    public ResponseEntity<?> addCertificateToAppUser(@RequestBody CertificateToUserForm certificateToUserForm) throws NotFoundException {
        appUserService.addCertificateToAppUser(certificateToUserForm.getAppUserId(), certificateToUserForm.getCertificateId());
        return ResponseEntity.ok().build();
    }
}
