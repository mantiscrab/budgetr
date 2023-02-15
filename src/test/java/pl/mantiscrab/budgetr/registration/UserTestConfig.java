package pl.mantiscrab.budgetr.registration;

import org.springframework.security.crypto.password.NoOpPasswordEncoder;

class UserTestConfig {
    static UserService userService() {
        return new UserConfig().userService(NoOpPasswordEncoder.getInstance(), new InMemoryUserRepository());
    }
}
