package pl.mantiscrab.budgetr.domain.bankaccount;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.mantiscrab.budgetr.domain.user.User;

import java.util.List;
import java.util.Optional;

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

    @Query("SELECT ba FROM " +
            "BankAccount ba JOIN ba.user " +
            "WHERE ba.user=:user " +
            "AND ba.name=:name")
    Optional<BankAccount> findByUserAndName(User user, String name);
}
