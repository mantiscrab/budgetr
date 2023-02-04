package pl.mantiscrab.budgetr.registration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import pl.mantiscrab.budgetr.registration.dto.UserDto;
import pl.mantiscrab.budgetr.registration.dto.UserRegisterDto;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;
import static pl.mantiscrab.budgetr.registration.TestUserData.sampleRegisterDto;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class RegistrationTest {
    private static final String LOCALHOST = "http://localhost";
    @LocalServerPort
    private int port;
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @DisplayName("Should register user when user with same email and username doesn't exist yet")
    void registrationTest() {
        //given there's no registered user
        //when I submit registration info
        UserRegisterDto registerRequest = sampleRegisterDto()
                .email("user@user")
                .username("fancyUsername")
                .password("password").build();
        ResponseEntity<UserDto> registerResponse = register(registerRequest);
        //then user is created
        assertEquals(HttpStatus.OK, registerResponse.getStatusCode());
        //and response data matches request data
        assertRegisterResponseMatchesRequest(registerResponse, registerRequest);

        //when user goes to main page providing his credentials
        ResponseEntity<String> response = restTemplate.withBasicAuth("fancyUsername", "password").getForEntity(baseUri() + "/registered", String.class);
        //then user is authenticated and response doesn't exist
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("fancyUsername", response.getBody());
    }

    private void assertRegisterResponseMatchesRequest(ResponseEntity<UserDto> registrationResponseEntity, UserRegisterDto registerRequest) {
        UserDto registrationResponse = extract(registrationResponseEntity);
        assertEquals(registerRequest.email(), registrationResponse.email());
        assertEquals(registerRequest.username(), registrationResponse.username());
    }

    private UserDto extract(ResponseEntity<UserDto> registerResponse) {
        return registerResponse.getBody();
    }

    private ResponseEntity<UserDto> register(UserRegisterDto registerDto) {
        return restTemplate.postForEntity(registerUri(), registerDto, UserDto.class);
    }

    private URI registerUri() {
        return MvcUriComponentsBuilder.relativeTo(baseUriComponentsBuilder()).withMethodCall(on
                        (UserController.class).register(null))
                .build().toUri();
    }

    private UriComponentsBuilder baseUriComponentsBuilder() {
        return UriComponentsBuilder.fromUriString(baseUri());
    }

    private String baseUri() {
        return LOCALHOST + ":" + port;
    }
}