package pl.mantiscrab.budgetr.auth;

import org.springframework.stereotype.Service;
import pl.mantiscrab.budgetr.domain.user.infrastructure.SignedInUsernameGetter;


@Service
public class SignedInUsernameGetterImpl implements SignedInUsernameGetter {
    @Override
    public String getUsername() {
        return "mantiscrab";
    }
}
