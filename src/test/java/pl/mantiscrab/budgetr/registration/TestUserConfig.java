package pl.mantiscrab.budgetr.registration;

class TestUserConfig {
    static UserService testUserService() {
        return new UserService(new TestPasswordEncoder(), new InMemoryUserRepository());
    }
}
