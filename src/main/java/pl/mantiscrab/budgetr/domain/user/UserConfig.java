package pl.mantiscrab.budgetr.domain.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mantiscrab.budgetr.domain.user.infrastructure.RecentlyAuthenticatedUsersPublisher;

@Configuration
class UserConfig {
    @Bean
    UserService userService(final RecentlyAuthenticatedUsersPublisher publisher, final UserRepository userRepository){
        UserService userService = new UserService(userRepository);
        publisher.subscribe(userService);
        return userService;
    }
}
