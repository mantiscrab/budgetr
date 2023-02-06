package pl.mantiscrab.budgetr.registration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class UserConfig {
    @Bean
    UserService userService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return new UserService(passwordEncoder, userRepository);
    }

    UserService userService() {
        return new UserService(NoOpPasswordEncoder.getInstance(), new InMemoryUserRepository());
    }
}
