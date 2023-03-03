package pl.mantiscrab.budgetr.domain.bankaccount;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mantiscrab.budgetr.domain.bankaccount.dto.BankAccountDto;
import pl.mantiscrab.budgetr.domain.bankaccount.exceptions.BankAccountWithSameNameAlreadyExistsException;
import pl.mantiscrab.budgetr.domain.user.DummySignedInUserGetter;
import pl.mantiscrab.budgetr.domain.user.User;

import java.util.List;
import java.util.Optional;

import static pl.mantiscrab.budgetr.domain.bankaccount.BankAccountTestDataProvider.sampleBankAccountDto;
import static pl.mantiscrab.budgetr.domain.user.UserTestDataProvider.sampleUser;

class BankAccountServiceTest {
    private BankAccountService bankAccountService;
    private DummySignedInUserGetter userGetter;

    @BeforeEach
    void initializeBankAccountService() {
        User signedInUser = sampleUser().username("mantiscrab").email("mantiscrab@budgetr.pl").build();
        userGetter = new DummySignedInUserGetter(signedInUser);
        this.bankAccountService = BankAccountTestConfig.bankAccountService(userGetter);
    }

    @Test
    void shouldGetAccountsAssociatedOnlyWithSignedInUser() {
        //given
        User user1 = sampleUser().username("user1").email("user1@email.com").build();
        User user2 = sampleUser().username("user2").email("user2@email.com").build();
        userGetter.setSignedInUser(user1);
        BankAccountDto user1BankAccountNo1 = bankAccountService.createBankAccount(sampleBankAccountDto().name("User1 Bank Account no.1").build());
        BankAccountDto user1BankAccountNo2 = bankAccountService.createBankAccount(sampleBankAccountDto().name("User1 Bank Account no.2").build());
        List<BankAccountDto> bankAccountsCreatedByUser1 = List.of(
                user1BankAccountNo1,
                user1BankAccountNo2
        );
        userGetter.setSignedInUser(user2);
        BankAccountDto user2BankAccountNo1 = bankAccountService.createBankAccount(sampleBankAccountDto().name("User2 Bank Account no.1").build());
        BankAccountDto user2BankAccountNo2 = bankAccountService.createBankAccount(sampleBankAccountDto().name("User2 Bank Account no.2").build());
        List<BankAccountDto> bankAccountsCreatedByUser2 = List.of(
                user2BankAccountNo1,
                user2BankAccountNo2
        );
        //when
        userGetter.setSignedInUser(user1);
        List<BankAccountDto> receivedUser1BankAccounts = bankAccountService.getAccounts();
        //then
        Assertions.assertEquals(bankAccountsCreatedByUser1, receivedUser1BankAccounts);

        //when
        userGetter.setSignedInUser(user2);
        List<BankAccountDto> receivedUser2BankAccounts = bankAccountService.getAccounts();
        //then
        Assertions.assertEquals(bankAccountsCreatedByUser2, receivedUser2BankAccounts);
    }

    @Test
    void shouldReturnEmptyOptionalWhenGetAccountAndUserIsNotOwner() {
        //given
        User user1 = sampleUser().username("user1").email("user1@email.com").build();
        User user2 = sampleUser().username("user2").email("user2@email.com").build();
        userGetter.setSignedInUser(user1);
        BankAccountDto createdBankAccount = bankAccountService.createBankAccount(
                sampleBankAccountDto().id(null).build());
        //when
        userGetter.setSignedInUser(user2);
        //then
        Optional<BankAccountDto> optionalAccount = bankAccountService.getAccount(createdBankAccount.id());
        Assertions.assertEquals(Optional.empty(), optionalAccount);
    }

    @Test
    void shouldCreateBankAccount() {
        //given
        BankAccountDto newBankAccount = sampleBankAccountDto().id(null).build();
        //when
        BankAccountDto createdBankAccount = bankAccountService.createBankAccount(newBankAccount);
        //then
        Assertions.assertEquals(newBankAccount.name(), createdBankAccount.name());
        Assertions.assertEquals(newBankAccount.initialBalance(), createdBankAccount.initialBalance());
    }

    @Test
    void shouldThrowExceptionWhenCreateBankAccountAndUserAlreadyHasAccountWithSameName() {
        //given
        BankAccountDto firstBankAccount = sampleBankAccountDto()
                .id(null)
                .name("Bank Inc.").build();
        bankAccountService.createBankAccount(firstBankAccount);
        BankAccountDto secondBankAccountWithSameName = sampleBankAccountDto()
                .id(null)
                .name("Bank Inc.").build();
        //when--then
        Assertions.assertThrows(BankAccountWithSameNameAlreadyExistsException.class,
                () -> bankAccountService.createBankAccount(secondBankAccountWithSameName));
    }

    @Test
    void shouldThrowExceptionWhenCreateBankAccountAndIdNotNull() {
        //given
        BankAccountDto bankAccount = sampleBankAccountDto().id(1L).build();
        //when--then
        Assertions.assertThrows(BankAccountWithSameNameAlreadyExistsException.class,
                () -> bankAccountService.createBankAccount(bankAccount));
    }
}