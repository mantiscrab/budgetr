package pl.mantiscrab.budgetr.domain.user;

import lombok.AllArgsConstructor;
import pl.mantiscrab.budgetr.domain.user.exceptions.BudgetrUserNotAuthenticatedException;
import pl.mantiscrab.budgetr.domain.user.infrastructure.SignedInUsernameProvider;
import pl.mantiscrab.budgetr.domain.user.infrastructure.UserNotAuthenticatedException;

@AllArgsConstructor
class SignedInUserProviderImpl implements SignedInUserProvider {
    private final SignedInUsernameProvider usernameProvider;
    private final UserRepository userRepository;

    @Override
    public User getUser() {
        try {
            String signedInUsername = usernameProvider.getUsername();
            return userRepository.findByUsername(signedInUsername)
                    .orElseThrow(BudgetrUserNotAuthenticatedException::new);
        } catch (UserNotAuthenticatedException e) {
            throw new BudgetrUserNotAuthenticatedException();
        }
    }
}
