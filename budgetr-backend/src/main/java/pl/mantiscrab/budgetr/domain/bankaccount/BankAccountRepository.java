package pl.mantiscrab.budgetr.domain.bankaccount;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.mantiscrab.budgetr.domain.user.User;

import java.util.List;

interface BankAccountRepository extends CrudRepository<BankAccount, Long> {
    @Query("SELECT ba FROM " +
            "BankAccount ba JOIN ba.user " +
            "WHERE ba.user=:user")
    List<BankAccount> findByUser(User user);
}
