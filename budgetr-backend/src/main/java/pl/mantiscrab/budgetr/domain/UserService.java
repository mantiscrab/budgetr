package pl.mantiscrab.budgetr.domain;

import lombok.AllArgsConstructor;
import pl.mantiscrab.budgetr.domain.infrastructure.RecentlyAuthenticatedUser;
import pl.mantiscrab.budgetr.domain.infrastructure.RecentlyAuthenticatedUsersSubscriber;

import java.util.Optional;

@AllArgsConstructor
class UserService implements RecentlyAuthenticatedUsersSubscriber {
    private final UserRepository userRepository;
    @Override
    public void userHasBeenAuthenticated(final RecentlyAuthenticatedUser authenticatedUserInfo) {
        Optional<User> optionalUser = userRepository.findByUsername(authenticatedUserInfo.username());
        if (optionalUser.isPresent()) {
            updateUser(authenticatedUserInfo, optionalUser.get());
        } else {
            createUser(authenticatedUserInfo);
        }
    }

    private void updateUser(final RecentlyAuthenticatedUser authenticatedUserInfo, final User user) {
        if (userChangedEmail(user, authenticatedUserInfo)) {
            User updatedUser = new User(user.getUsername(), authenticatedUserInfo.email());
            userRepository.save(updatedUser);
        }
    }

    private boolean userChangedEmail(final User user, final RecentlyAuthenticatedUser authenticatedUser) {
        return !user.getEmail().equals(authenticatedUser.email());
    }

    private User createUser(final RecentlyAuthenticatedUser authenticatedUserInfo) {
        User user = new User(authenticatedUserInfo.username(), authenticatedUserInfo.email());
        userRepository.save(user);
        return user;
    }
}
