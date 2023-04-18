package pl.mantiscrab.budgetr.domain.infrastructure;

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

    public void notify(RecentlyAuthenticatedUser user){
        for (RecentlyAuthenticatedUsersSubscriber subscriber : subscribers) {
            subscriber.userHasBeenAuthenticated(user);
        }
    }
}
