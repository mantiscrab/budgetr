package pl.mantiscrab.budgetr.domain;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.mantiscrab.budgetr.domain.dto.BankAccountDto;

import java.util.List;
import java.util.Optional;

interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT NEW pl.mantiscrab.budgetr.domain.dto.BankAccountDto(INDEX(ba), ba.name, ba.initialBalance) FROM User user JOIN user.bankAccounts ba WHERE user=:user")
    List<BankAccountDto> findByUser(User user);

    @Query("SELECT NEW pl.mantiscrab.budgetr.domain.dto.BankAccountDto(INDEX(ba), ba.name, ba.initialBalance) FROM User u JOIN u.bankAccounts ba WHERE u =:user AND INDEX(ba)=:index")
    BankAccountDto findByUserAndIndex(User user, Integer index);
}
