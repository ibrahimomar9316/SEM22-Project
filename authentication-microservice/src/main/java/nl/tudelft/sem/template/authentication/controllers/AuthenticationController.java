package nl.tudelft.sem.template.authentication.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import nl.tudelft.sem.template.authentication.authentication.JwtTokenGenerator;
import nl.tudelft.sem.template.authentication.authentication.JwtUserDetailsService;
import nl.tudelft.sem.template.authentication.dataTransferObjects.UserDTO;
import nl.tudelft.sem.template.authentication.domain.user.*;
import nl.tudelft.sem.template.authentication.models.AuthenticationRequestModel;
import nl.tudelft.sem.template.authentication.models.AuthenticationResponseModel;
import nl.tudelft.sem.template.authentication.models.RegistrationRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class AuthenticationController {

    private final transient AuthenticationManager authenticationManager;

    private final transient JwtTokenGenerator jwtTokenGenerator;

    private final transient JwtUserDetailsService jwtUserDetailsService;

    private final transient RegistrationService registrationService;

    /**
     * Instantiates a new UsersController.
     *
     * @param authenticationManager the authentication manager
     * @param jwtTokenGenerator     the token generator
     * @param jwtUserDetailsService the user service
     * @param registrationService   the registration service
     */
    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtTokenGenerator jwtTokenGenerator,
                                    JwtUserDetailsService jwtUserDetailsService,
                                    RegistrationService registrationService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.registrationService = registrationService;
    }

    /**
     * Endpoint for authentication.
     *
     * @param request The login model
     * @return JWT token if the login is successful
     * @throws Exception if the user does not exist or the password is incorrect
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseModel> authenticate(@RequestBody AuthenticationRequestModel request)
            throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getNetId(),
                            request.getPassword()));
        } catch (DisabledException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(request.getNetId());
        final String jwtToken = jwtTokenGenerator.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponseModel(jwtToken));
    }

    /**
     * Endpoint for registration.
     *
     * @param request The registration model
     * @return 200 OK if the registration is successful
     * @throws Exception if a user with this netid already exists
     */
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegistrationRequestModel request) throws Exception {
        NetId netId;
        Password password;
        try {
            netId = new NetId(request.getNetId());
            password = new Password(request.getPassword());
            registrationService.registerUser(netId, password);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        //TOKEN
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(request.getNetId());
        final String jwtToken = jwtTokenGenerator.generateToken(userDetails);
        ResponseEntity<AuthenticationResponseModel> token = ResponseEntity.ok(new AuthenticationResponseModel(jwtToken));

        //DONE
        UserDTO requestBody = new UserDTO(
                netId.toString(),
                password.toString()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //System.out.println(token.getBody().getToken().toString());
        headers.setBearerAuth(token.getBody().getToken().toString());

        String json = new ObjectMapper().writeValueAsString(requestBody);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        System.out.println(entity.getHeaders().toString());

        ResponseEntity<UserDTO> obj = new RestTemplate()
                .postForEntity("http://localhost:8082/api/user/save", entity, UserDTO.class);
        if (obj.getStatusCode().is2xxSuccessful())
            return ResponseEntity.ok().build();
        else throw new NotFoundException("Incorrectly saved in user microservice");
        //return ResponseEntity.ok().build();
    }
}
