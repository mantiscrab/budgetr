package pl.mantiscrab.budgetr.domain.bankaccount;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mantiscrab.budgetr.domain.user.DummySignedInUserGetter;
import pl.mantiscrab.budgetr.domain.user.User;

import static pl.mantiscrab.budgetr.domain.user.UserTestDataProvider.sampleUser;

class BankAccountServiceTest {
    private BankAccountService bankAccountService;

    @BeforeEach
    void initializeBankAccountService() {
        User signedInUser = sampleUser().username("mantiscrab").email("mantiscrab@budgetr.pl").build();
        DummySignedInUserGetter userGetter = new DummySignedInUserGetter(signedInUser);
        this.bankAccountService = BankAccountTestConfig.bankAccountService(userGetter);
    }

    @Test
    void shouldCreateBankAccount() {
        throw new NotImplementedException();
    }

    @Test
    void shouldThrowExceptionWhenCreateBankAccountAndAccountWithSameNameAlreadyExists() {
        throw new NotImplementedException();
    }

    @Test
    void shouldUpdateBankAccount() {
        throw new NotImplementedException();
    }

    @Test
    void shouldThrowExceptionWhenUpdateBankAccountAndAccountWithSameNameAlreadyExists() {
        throw new NotImplementedException();
    }

    @Test
    void shouldThrowExceptionWhenUpdateBankAccountAndAccountWithSpecifiedIdDoesntExist() {
        throw new NotImplementedException();
    }

    @Test
    void shouldDeleteBankAccount() {
        throw new NotImplementedException();
    }

    @Test
    void shouldNotThrowExceptionWhenDeleteAccountAndAccountDoesntExist() {
        throw new NotImplementedException();
    }
}