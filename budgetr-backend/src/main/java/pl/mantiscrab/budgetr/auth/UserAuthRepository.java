package pl.mantiscrab.budgetr.auth;


import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

interface UserAuthRepository extends CrudRepository<UserAuth, String> {
    Optional<UserAuth> findByEmail(String email);

    Optional<UserAuth> findByUsername(String username);
}
