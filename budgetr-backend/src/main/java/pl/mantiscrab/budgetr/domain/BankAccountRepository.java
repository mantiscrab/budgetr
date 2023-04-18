package pl.mantiscrab.budgetr.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

interface BankAccountRepository extends CrudRepository<BankAccount, Long> {
    @Query("SELECT ba FROM " +
            "BankAccount ba JOIN ba.user " +
            "WHERE ba.user=:user")
    List<BankAccount> findByUser(User user);

    @Query("SELECT ba FROM " +
            "BankAccount ba JOIN ba.user " +
            "WHERE ba.user=:user " +
            "AND ba.id=:id")
    Optional<BankAccount> findByUserAndId(User user, Long id);
    Set<BankAccount> findByUserAndName(User user, String name);
    void deleteByUserAndId(User user, Long id);
}
