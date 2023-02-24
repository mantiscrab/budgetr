package pl.mantiscrab.budgetr.auth;

import org.springframework.dao.DataIntegrityViolationException;
import pl.mantiscrab.budgetr.InMemoryDummyCrudRepository;

import java.util.Objects;
import java.util.Optional;

class InMemoryUserAuthRepository extends InMemoryDummyCrudRepository<UserAuth, String> implements UserAuthRepository {
    @Override
    public <S extends UserAuth> S save(S user) {
        if(findByEmail(user.getEmail()).isPresent())
            throw new DataIntegrityViolationException(
                    "User with email: \"" + user.getEmail() + "\" already exists");
        map.put(user.getUsername(), user);
        return user;
    }

    @Override
    public Optional<UserAuth> findByEmail(String email) {
        return map.values().stream()
                .filter(user -> Objects.equals(user.getEmail(), email))
                .findFirst();
    }

    @Override
    public Optional<UserAuth> findByUsername(String username) {
        return map.values().stream()
                .filter(user -> Objects.equals(user.getUsername(), username))
                .findFirst();
    }
}
