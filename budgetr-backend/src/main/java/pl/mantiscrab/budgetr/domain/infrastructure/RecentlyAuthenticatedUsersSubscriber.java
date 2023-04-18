package pl.mantiscrab.budgetr.domain.infrastructure;

public interface RecentlyAuthenticatedUsersSubscriber {
    void userHasBeenAuthenticated(RecentlyAuthenticatedUser recentlyAuthenticatedUser);
}
