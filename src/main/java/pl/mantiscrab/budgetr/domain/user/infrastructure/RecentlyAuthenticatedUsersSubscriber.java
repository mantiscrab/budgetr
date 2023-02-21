package pl.mantiscrab.budgetr.domain.user.infrastructure;

public interface RecentlyAuthenticatedUsersSubscriber {
    void userHasBeenAuthenticated(RecentlyAuthenticatedUser recentlyAuthenticatedUser);
}
