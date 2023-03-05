package pl.mantiscrab.budgetr.auth;

import org.springframework.stereotype.Service;
import pl.mantiscrab.budgetr.domain.user.infrastructure.SignedInUsernameGetter;
import pl.mantiscrab.budgetr.domain.user.infrastructure.UserNotAuthenticatedException;

@Service
class DummySignedInUsernameGetter implements SignedInUsernameGetter {
    @Override
    public String getUsername() throws UserNotAuthenticatedException {
        return "mantiscrab";
    }
}
