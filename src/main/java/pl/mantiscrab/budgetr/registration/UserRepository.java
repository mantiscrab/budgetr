package pl.mantiscrab.budgetr.registration;


import org.springframework.data.repository.CrudRepository;

interface UserRepository extends CrudRepository<User, Long> {
}
