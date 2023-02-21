package pl.mantiscrab.budgetr.domain.user;


import org.springframework.data.repository.CrudRepository;

interface UserRepository extends CrudRepository<User, Long> {
}
