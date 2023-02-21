package pl.mantiscrab.budgetr.domain.user.infrastructure;

import java.util.HashSet;
import java.util.Set;

public abstract class RecentlyAuthenticatedUsersPublisher {
    private final Set<RecentlyAuthenticatedUsersSubscriber> subscribers;

    protected RecentlyAuthenticatedUsersPublisher() {
        this.subscribers = new HashSet<>();
    }

    public void subscribe(RecentlyAuthenticatedUsersSubscriber subscriber){
        subscribers.add(subscriber);
    }

    public void inform(RecentlyAuthenticatedUser user){
        for (RecentlyAuthenticatedUsersSubscriber subscriber : subscribers) {
            subscriber.userHasBeenAuthenticated(user);
        }
    }
}
