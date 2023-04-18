package pl.mantiscrab.budgetr.domain;

import lombok.AllArgsConstructor;
import pl.mantiscrab.budgetr.domain.infrastructure.RecentlyAuthenticatedUser;
import pl.mantiscrab.budgetr.domain.infrastructure.RecentlyAuthenticatedUsersSubscriber;

@AllArgsConstructor
class UserService implements RecentlyAuthenticatedUsersSubscriber {
    private final UserRepository userRepository;

    @Override
    public void userHasBeenAuthenticated(final RecentlyAuthenticatedUser authenticatedUserInfo) {
        final String username = authenticatedUserInfo.username();
        final String email = authenticatedUserInfo.email();
        userRepository
                .findByUsername(username)
                .ifPresentOrElse(
                        user -> user.updateEmail(email),
                        () -> createUser(authenticatedUserInfo));
    }

    private void createUser(final RecentlyAuthenticatedUser authenticatedUserInfo) {
        User user = new User(authenticatedUserInfo.username(), authenticatedUserInfo.email());
        userRepository.save(user);
    }
}
