package pl.mantiscrab.budgetr.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class UserConfig {
    @Bean
    UserService userService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        return new UserService(passwordEncoder, userRepository);
    }
}
