package pl.mantiscrab.budgetr.domain.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mantiscrab.budgetr.domain.user.infrastructure.RecentlyAuthenticatedUsersPublisher;
import pl.mantiscrab.budgetr.domain.user.infrastructure.SignedInUsernameGetter;

@Configuration
class UserConfig {
    @Bean
    UserService userService(final RecentlyAuthenticatedUsersPublisher publisher, final UserRepository userRepository) {
        UserService userService = new UserService(userRepository);
        publisher.subscribe(userService);
        return userService;
    }

    @Bean
    SignedInUserGetter signedInUserGetter(final SignedInUsernameGetter usernameGetter, final UserRepository userRepository) {
        return new SignedInUserGetter(usernameGetter, userRepository);
    }
}
