package pl.mantiscrab.budgetr.domain.user;

import lombok.AllArgsConstructor;
import pl.mantiscrab.budgetr.domain.user.infrastructure.SignedInUsernameGetter;

@AllArgsConstructor
public class SignedInUserGetter {

    private final SignedInUsernameGetter usernameGetter;
    private final UserRepository userRepository;
    public User getUser() {
        return userRepository.findByUsername(usernameGetter.getUsername()).get();
    }
}
