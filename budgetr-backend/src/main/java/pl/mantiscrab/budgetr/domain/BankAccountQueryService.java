package pl.mantiscrab.budgetr.domain;

import lombok.AllArgsConstructor;
import pl.mantiscrab.budgetr.domain.dto.BankAccountDto;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class BankAccountQueryService {
    private final SignedInUserProvider userProvider;
    private final UserRepository accountRepository;

    public List<BankAccountDto> getAccounts() {
        User user = userProvider.getUser();
        return accountRepository.findByUser(user);
    }

    public Optional<BankAccountDto> findByIndex(int index) {
        User user = userProvider.getUser();
        return Optional.ofNullable(accountRepository.findByUserAndIndex(user
                , index
        ));
    }

    public BankAccountDto getByIndex(int index) {
        User user = userProvider.getUser();
        return accountRepository.findByUserAndIndex(user
                , index
        );
    }
}
