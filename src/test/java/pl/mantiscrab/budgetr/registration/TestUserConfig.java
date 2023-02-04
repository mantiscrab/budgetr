package pl.mantiscrab.budgetr.registration;

import org.springframework.security.crypto.password.NoOpPasswordEncoder;

class TestUserConfig {
    static UserService testUserService() {
        return new UserService(NoOpPasswordEncoder.getInstance(), new InMemoryUserRepository());
    }
}
