package pl.mantiscrab.budgetr.domain;

import java.util.ArrayList;

class UserTestDataProvider {
    public static User.UserBuilder sampleUser() {
        return User.builder()
                .email("sampleUser@email.com")
                .username("sampleUsername")
                .bankAccounts(new ArrayList<>());
    }
}
