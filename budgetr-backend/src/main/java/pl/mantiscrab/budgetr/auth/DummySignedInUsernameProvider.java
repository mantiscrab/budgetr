package pl.mantiscrab.budgetr.auth;

import org.springframework.stereotype.Service;
import pl.mantiscrab.budgetr.domain.user.infrastructure.SignedInUsernameProvider;
import pl.mantiscrab.budgetr.domain.user.infrastructure.UserNotAuthenticatedException;

@Service
class DummySignedInUsernameProvider implements SignedInUsernameProvider {
    @Override
    public String getUsername() throws UserNotAuthenticatedException {
        return "mantiscrab";
    }
}
