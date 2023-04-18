package pl.mantiscrab.budgetr.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mantiscrab.budgetr.domain.infrastructure.RecentlyAuthenticatedUsersPublisher;
import pl.mantiscrab.budgetr.domain.infrastructure.SignedInUsernameProvider;

@Configuration
class UserConfig {
    @Bean
    UserService userService(final RecentlyAuthenticatedUsersPublisher publisher, final UserRepository userRepository) {
        UserService userService = new UserService(userRepository);
        publisher.subscribe(userService);
        return userService;
    }

    @Bean
    SignedInUserProvider signedInUserGetter(final SignedInUsernameProvider usernameGetter, final UserRepository userRepository) {
        return new SignedInUserProviderImpl(usernameGetter, userRepository);
    }
}
