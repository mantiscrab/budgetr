package pl.mantiscrab.budgetr.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import pl.mantiscrab.budgetr.UriProvider;
import pl.mantiscrab.budgetr.auth.dto.UserDto;
import pl.mantiscrab.budgetr.auth.dto.UserRegisterDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static pl.mantiscrab.budgetr.auth.UserAuthTestDataProvider.sampleRegisterDto;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
class RegistrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    TestRestTemplate restTemplate;
    RegistrationHelper registrationHelper;
    UriProvider uriProvider;

    @BeforeEach
    void initialize() {
        registrationHelper = new RegistrationHelper(restTemplate, port);
        uriProvider = new UriProvider(port);
    }


    @Test
    @DisplayName("Should register user when user with same email and username doesn't exist yet")
    void registrationTest() {
        //given there's no registered user
        //when I submit registration info
        UserRegisterDto registerRequest = sampleRegisterDto()
                .email("user@user")
                .username("fancyUsername")
                .password("password").build();
        ResponseEntity<UserDto> registerResponse = registrationHelper.registerUser(registerRequest);
        //then user is created
        assertEquals(HttpStatus.OK, registerResponse.getStatusCode());
        //and response data matches request data
        assertRegisterResponseMatchesRequest(registerResponse, registerRequest);
    }

    private void assertRegisterResponseMatchesRequest(ResponseEntity<UserDto> registrationResponseEntity, UserRegisterDto registerRequest) {
        UserDto registrationResponse = registrationResponseEntity.getBody();
        assertEquals(registerRequest.email(), registrationResponse.email());
        assertEquals(registerRequest.username(), registrationResponse.username());
    }
}