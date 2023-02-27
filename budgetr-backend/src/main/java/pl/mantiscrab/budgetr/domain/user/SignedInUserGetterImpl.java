package pl.mantiscrab.budgetr.domain.user;

import lombok.AllArgsConstructor;
import pl.mantiscrab.budgetr.domain.user.exceptions.BudgetrUserNotAuthenticatedException;
import pl.mantiscrab.budgetr.domain.user.infrastructure.SignedInUsernameGetter;
import pl.mantiscrab.budgetr.domain.user.infrastructure.UserNotAuthenticatedException;

@AllArgsConstructor
class SignedInUserGetterImpl implements SignedInUserGetter {
    private final SignedInUsernameGetter usernameGetter;
    private final UserRepository userRepository;

    @Override
    public User getUser() {
        try {
            String signedInUsername = usernameGetter.getUsername();
            return userRepository.findByUsername(signedInUsername)
                    .orElseThrow(BudgetrUserNotAuthenticatedException::new);
        } catch (UserNotAuthenticatedException e) {
            throw new BudgetrUserNotAuthenticatedException();
        }
    }
}
