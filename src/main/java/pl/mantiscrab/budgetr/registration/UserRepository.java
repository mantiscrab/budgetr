package pl.mantiscrab.budgetr.registration;


import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
