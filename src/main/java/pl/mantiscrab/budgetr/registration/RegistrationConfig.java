package pl.mantiscrab.budgetr.registration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class RegistrationConfig {
    @Bean
    RegistrationFacade registrationFacade(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return new RegistrationFacade(passwordEncoder, userRepository);
    }
}
