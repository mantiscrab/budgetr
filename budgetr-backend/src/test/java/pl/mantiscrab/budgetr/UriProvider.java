package pl.mantiscrab.budgetr;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@AllArgsConstructor
public class UriProvider {
    private static final String LOCALHOST = "http://localhost";
    private final int localServerPort;


    public URI getUriOn(ResponseEntity<?> invocationInfo) {
        MvcUriComponentsBuilder builder =
                getMvcUriComponentsBuilderRelativeToBaseUri();
        return builder.withMethodCall(invocationInfo)
                .build().toUri();
    }

    private MvcUriComponentsBuilder getMvcUriComponentsBuilderRelativeToBaseUri() {
        return MvcUriComponentsBuilder.relativeTo(baseUriComponentsBuilder());
    }

    private UriComponentsBuilder baseUriComponentsBuilder() {
        return UriComponentsBuilder.fromUriString(baseUri());
    }

    private String baseUri() {
        return LOCALHOST + ":" + localServerPort;
    }
}
