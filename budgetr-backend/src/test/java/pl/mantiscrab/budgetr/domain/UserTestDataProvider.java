package pl.mantiscrab.budgetr.domain;

public class UserTestDataProvider {
    public static User.UserBuilder sampleUser() {
        return User.builder()
                .email("sampleUser@email.com")
                .username("sampleUsername");
    }
}
