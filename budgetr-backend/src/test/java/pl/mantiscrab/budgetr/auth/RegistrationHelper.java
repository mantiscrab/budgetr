package pl.mantiscrab.budgetr.auth;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import pl.mantiscrab.budgetr.UriProvider;
import pl.mantiscrab.budgetr.auth.dto.UserDto;
import pl.mantiscrab.budgetr.auth.dto.UserRegisterDto;

import java.net.URI;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

public class RegistrationHelper {
    private final TestRestTemplate restTemplate;
    private final UriProvider uriProvider;

    public RegistrationHelper(TestRestTemplate testRestTemplate, int localServerPort) {
        restTemplate = testRestTemplate;
        uriProvider = new UriProvider(localServerPort);
    }


    public ResponseEntity<UserDto> registerUser(UserRegisterDto registerRequest) {
        URI registartionUri = uriProvider.getUriOn(on(UserController.class).register(null));
        return restTemplate.postForEntity(
                registartionUri,
                registerRequest,
                UserDto.class);
    }
}
