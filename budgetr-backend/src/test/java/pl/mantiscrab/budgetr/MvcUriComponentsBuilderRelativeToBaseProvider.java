package pl.mantiscrab.budgetr;

import lombok.AllArgsConstructor;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

@AllArgsConstructor
public class MvcUriComponentsBuilderRelativeToBaseProvider {
    private static final String LOCALHOST = "http://localhost";
    private final int localServerPort;

    public MvcUriComponentsBuilder get() {
        return MvcUriComponentsBuilder.relativeTo(baseUriComponentsBuilder());
    }

    private UriComponentsBuilder baseUriComponentsBuilder() {
        return UriComponentsBuilder.fromUriString(baseUri());
    }

    private String baseUri() {
        return LOCALHOST + ":" + localServerPort;
    }
}
