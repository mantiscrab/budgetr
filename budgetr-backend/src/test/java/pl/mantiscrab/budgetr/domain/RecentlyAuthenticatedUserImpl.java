package pl.mantiscrab.budgetr.domain;

import lombok.Builder;
import pl.mantiscrab.budgetr.domain.infrastructure.RecentlyAuthenticatedUser;

@Builder
record RecentlyAuthenticatedUserImpl(String username, String email) implements RecentlyAuthenticatedUser {
}
