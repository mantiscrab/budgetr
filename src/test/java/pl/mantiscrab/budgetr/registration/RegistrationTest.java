package pl.mantiscrab.budgetr.registration;

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

@SpringBootTest(webEnvironment = RANDOM_PORT)
class RegistrationTest {
    private static final String LOCALHOST = "http://localhost";
    @LocalServerPort
    private int port;
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void registrationTest() {
        //given there's no registered user
        //when I submit registration info
        UserRegisterDto registerRequest = sampleRegisterDto().build();
        ResponseEntity<UserDto> registerResponse = register(registerRequest);
        //then user is created
        assertEquals(HttpStatus.OK, registerResponse.getStatusCode());
        //and response data matches request data
        assertRegisterResponseMatchesRequest(registerResponse,registerRequest);
    }

    private UserRegisterDto.UserRegisterDtoBuilder sampleRegisterDto() {
        return UserRegisterDto.builder()
                .email("user@user")
                .username("username")
                .password("password");
    }

    private void assertRegisterResponseMatchesRequest(ResponseEntity<UserDto> registerResponse, UserRegisterDto registerRequest) {
        assertRegisterResponseMatchesRequest(extract(registerResponse), registerRequest);
    }

    private void assertRegisterResponseMatchesRequest(UserDto registrationResponse, UserRegisterDto registerRequest) {
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

    private  UriComponentsBuilder baseUriComponentsBuilder() {
        return UriComponentsBuilder.fromUriString(baseUri());
    }

    private String baseUri() {
        return LOCALHOST + ":" + port;
    }
}