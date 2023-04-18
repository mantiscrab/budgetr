package pl.mantiscrab.budgetr.domain;

import lombok.AllArgsConstructor;
import pl.mantiscrab.budgetr.domain.infrastructure.UserNotAuthenticatedException;
import pl.mantiscrab.budgetr.domain.exceptions.BudgetrUserNotAuthenticatedException;
import pl.mantiscrab.budgetr.domain.infrastructure.SignedInUsernameProvider;

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
