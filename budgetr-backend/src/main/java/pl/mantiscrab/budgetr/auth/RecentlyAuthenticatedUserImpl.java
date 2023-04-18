package pl.mantiscrab.budgetr.auth;

import pl.mantiscrab.budgetr.domain.infrastructure.RecentlyAuthenticatedUser;

record RecentlyAuthenticatedUserImpl(String username, String email) implements RecentlyAuthenticatedUser {

}
