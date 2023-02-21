package pl.mantiscrab.budgetr.domain.user;

import lombok.AllArgsConstructor;
import pl.mantiscrab.budgetr.domain.user.infrastructure.RecentlyAuthenticatedUser;
import pl.mantiscrab.budgetr.domain.user.infrastructure.RecentlyAuthenticatedUsersSubscriber;

@AllArgsConstructor
class UserService implements RecentlyAuthenticatedUsersSubscriber {
    private final UserRepository userRepository;
    @Override
    public void userHasBeenAuthenticated(final RecentlyAuthenticatedUser authenticatedUser) {
        User user = new User(authenticatedUser.username(), authenticatedUser.email());
        userRepository.save(user);
    }
}
