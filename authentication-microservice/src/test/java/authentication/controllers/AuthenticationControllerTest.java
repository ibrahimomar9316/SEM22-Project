package authentication.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import authentication.authentication.JwtTokenGenerator;
import authentication.authentication.JwtUserDetailsService;
import authentication.domain.user.NetId;
import authentication.domain.user.Password;
import authentication.domain.user.RegistrationService;
import authentication.models.AuthenticationRequestModel;
import authentication.models.AuthenticationResponseModel;
import authentication.models.RegistrationRequestModel;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
class AuthenticationControllerTest {

    @Autowired
    private AuthenticationController auth;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenGenerator jwtTokenGenerator;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private RegistrationService registrationService;

    @Test
    void authenticate() throws Exception {
        when(jwtTokenGenerator.generateToken(any(UserDetails.class))).thenReturn("kek");
        ResponseEntity<AuthenticationResponseModel> res = auth
                .authenticate(new AuthenticationRequestModel("name", "pass"));
        assertThat(res.getStatusCodeValue()).isEqualTo(200);
        assertThat(res.getBody()).isEqualTo(new AuthenticationResponseModel(null));

        doThrow(DisabledException.class).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        assertThrows(ResponseStatusException.class, () -> {
            auth.authenticate(new AuthenticationRequestModel("name", "pass"));
        });

        doThrow(BadCredentialsException.class).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        assertThrows(ResponseStatusException.class, () -> {
            auth.authenticate(new AuthenticationRequestModel("name", "pass"));
        });
    }

    @Test
    void register() throws Exception {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        auth.setRestTemplate(restTemplate);

        when(restTemplate.postForEntity(any(String.class), any(), any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
        ResponseEntity<String> res = auth.register(new RegistrationRequestModel("id", "p"));
        assertThat(res.getStatusCodeValue()).isEqualTo(200);
        assertThat(res.getBody().contains("Successfully registered user: ")).isTrue();

        when(restTemplate.postForEntity(any(String.class), any(), any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        assertThrows(NotFoundException.class, () -> {
            auth.register(new RegistrationRequestModel("id", "pas"));
        });

        doThrow(IllegalArgumentException.class).when(registrationService)
                .registerUser(new NetId("id"), new Password("pass"));
        assertThrows(ResponseStatusException.class, () -> {
            auth.register(new RegistrationRequestModel("id", "pass"));
        });

    }
}