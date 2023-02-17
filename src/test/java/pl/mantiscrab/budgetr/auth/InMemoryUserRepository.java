package pl.mantiscrab.budgetr.auth;

import org.springframework.dao.DataIntegrityViolationException;
import pl.mantiscrab.budgetr.InMemoryDummyCrudRepository;

import java.util.Objects;
import java.util.Optional;

class InMemoryUserRepository extends InMemoryDummyCrudRepository<User, String> implements UserRepository {
    @Override
    public <S extends User> S save(S user) {
        if(findByEmail(user.getEmail()).isPresent())
            throw new DataIntegrityViolationException(
                    "User with email: \"" + user.getEmail() + "\" already exists");
        map.put(user.getUsername(), user);
        return user;
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
