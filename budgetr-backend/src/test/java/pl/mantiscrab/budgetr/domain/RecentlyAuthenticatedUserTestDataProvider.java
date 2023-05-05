package pl.mantiscrab.budgetr.domain;

public class RecentlyAuthenticatedUserTestDataProvider {
    public static RecentlyAuthenticatedUserImpl.RecentlyAuthenticatedUserImplBuilder
            sampleUser() {
        return RecentlyAuthenticatedUserImpl.builder()
                .email("sampleUser@email.com")
                .username("sampleUsername");
    }
}
