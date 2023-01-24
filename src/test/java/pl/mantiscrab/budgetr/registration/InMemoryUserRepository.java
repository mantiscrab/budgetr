package pl.mantiscrab.budgetr.registration;

import java.util.Objects;
import java.util.Optional;

class InMemoryUserRepository extends InMemoryDummyCrudRepository<User, Long> implements UserRepository{
    @Override
    public <S extends User> S save(S user) {
        if (user.getId() == null) {
            user = (S) setIndexAsId(user);
        }
        map.put(index, user);
        return user;
    }

    private <S extends User> User setIndexAsId(S user) {
        return new User(index++, user.getEmail(), user.getUsername(), user.getPassword());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return map.values().stream()
                .filter(user -> Objects.equals(user.getEmail(), email))
                .findFirst();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return map.values().stream()
                .filter(user -> Objects.equals(user.getUsername(), username))
                .findFirst();
    }
}
