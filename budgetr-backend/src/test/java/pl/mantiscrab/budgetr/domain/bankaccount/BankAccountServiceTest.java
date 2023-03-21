package pl.mantiscrab.budgetr.domain.bankaccount;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mantiscrab.budgetr.domain.bankaccount.dto.BankAccountDto;
import pl.mantiscrab.budgetr.domain.bankaccount.exceptions.BankAccountWithSameNameAlreadyExistsException;
import pl.mantiscrab.budgetr.domain.exceptions.OperationNotAllowedException;
import pl.mantiscrab.budgetr.domain.user.DummySignedInUserProvider;
import pl.mantiscrab.budgetr.domain.user.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static pl.mantiscrab.budgetr.domain.bankaccount.BankAccountTestDataProvider.sampleBankAccountDto;
import static pl.mantiscrab.budgetr.domain.user.UserTestDataProvider.sampleUser;

class BankAccountServiceTest {
    private BankAccountService bankAccountService;
    private DummySignedInUserProvider userProvider;

    @BeforeEach
    void initializeBankAccountService() {
        User signedInUser = sampleUser().username("mantiscrab").email("mantiscrab@budgetr.pl").build();
        userProvider = new DummySignedInUserProvider(signedInUser);
        this.bankAccountService = BankAccountTestConfig.bankAccountService(userProvider);
    }

    @Test
    void shouldGetAccountsAssociatedOnlyWithSignedInUser() {
        //given
        User user1 = sampleUser().username("user1").email("user1@email.com").build();
        User user2 = sampleUser().username("user2").email("user2@email.com").build();
        userProvider.setSignedInUser(user1);
        BankAccountDto user1BankAccountNo1 = bankAccountService.createBankAccount(sampleBankAccountDto().id(null).name("User1 Bank Account no.1").build());
        BankAccountDto user1BankAccountNo2 = bankAccountService.createBankAccount(sampleBankAccountDto().id(null).name("User1 Bank Account no.2").build());
        List<BankAccountDto> bankAccountsCreatedByUser1 = List.of(
                user1BankAccountNo1,
                user1BankAccountNo2
        );
        userProvider.setSignedInUser(user2);
        BankAccountDto user2BankAccountNo1 = bankAccountService.createBankAccount(sampleBankAccountDto().id(null).name("User2 Bank Account no.1").build());
        BankAccountDto user2BankAccountNo2 = bankAccountService.createBankAccount(sampleBankAccountDto().id(null).name("User2 Bank Account no.2").build());
        List<BankAccountDto> bankAccountsCreatedByUser2 = List.of(
                user2BankAccountNo1,
                user2BankAccountNo2
        );
        //when
        userProvider.setSignedInUser(user1);
        List<BankAccountDto> receivedUser1BankAccounts = bankAccountService.getAccounts();
        //then
        Assertions.assertEquals(bankAccountsCreatedByUser1, receivedUser1BankAccounts);

        //when
        userProvider.setSignedInUser(user2);
        List<BankAccountDto> receivedUser2BankAccounts = bankAccountService.getAccounts();
        //then
        Assertions.assertEquals(bankAccountsCreatedByUser2, receivedUser2BankAccounts);
    }

    @Test
    void shouldReturnEmptyOptionalWhenGetAccountAndUserIsNotOwner() {
        //given
        User user1 = sampleUser().username("user1").email("user1@email.com").build();
        User user2 = sampleUser().username("user2").email("user2@email.com").build();
        userProvider.setSignedInUser(user1);
        BankAccountDto createdBankAccount = bankAccountService.createBankAccount(
                sampleBankAccountDto().id(null).build());
        //when
        userProvider.setSignedInUser(user2);
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
        Assertions.assertThrows(OperationNotAllowedException.class,
                () -> bankAccountService.createBankAccount(bankAccount));
    }

    @Test
    void shouldUpdateBankAccount() {
        //given
        BankAccountDto newBankAccount = sampleBankAccountDto()
                .id(null)
                .name("Bank Inc.")
                .initialBalance(BigDecimal.ZERO).build();
        BankAccountDto createdBankAccount = bankAccountService.createBankAccount(newBankAccount);
        //when
        BankAccountDto bankAccountToBeUpdated = sampleBankAccountDto()
                .id(createdBankAccount.id())
                .name("Bank Inc.")
                .initialBalance(BigDecimal.ONE).build();
        BankAccountDto updatedBankAccount = bankAccountService.updateBankAccount(bankAccountToBeUpdated.id(), bankAccountToBeUpdated);
        //then
        Assertions.assertEquals(bankAccountToBeUpdated, updatedBankAccount);
        //when
        BankAccountDto getUpdatedBankAccount = bankAccountService.getAccount(updatedBankAccount.id()).get();
        //then
        Assertions.assertEquals(bankAccountToBeUpdated, getUpdatedBankAccount);
    }

    @Test
    void shouldThrowExceptionWhenUpdateBankAccountAndUserIsNotOwner() {
        //given
        User firstUser = sampleUser().username("firstUser").email("firstUser@email.com").build();
        User secondUser = sampleUser().username("secondUser").email("secondUser@email.com").build();
        userProvider.setSignedInUser(firstUser);
        BankAccountDto firstUserBankAccount = bankAccountService.createBankAccount(sampleBankAccountDto().id(null).build());
        //when--then
        userProvider.setSignedInUser(secondUser);
        Assertions.assertThrows(OperationNotAllowedException.class, () -> bankAccountService.updateBankAccount(firstUserBankAccount.id(), firstUserBankAccount));
    }

    @Test
    void shouldThrowExceptionWhenUpdateBankAccountAndAccountWithSameNameAlreadyExists() {
        //given
        BankAccountDto firstBankAccount = sampleBankAccountDto()
                .id(null)
                .name("Bank 1 Inc.").build();
        BankAccountDto secondBankAccount = sampleBankAccountDto()
                .id(null)
                .name("Bank 2 Inc.").build();
        bankAccountService.createBankAccount(firstBankAccount);
        BankAccountDto createdSecondBankAccount = bankAccountService.createBankAccount(secondBankAccount);
        //when--then
        BankAccountDto secondBankAccountToBeUpdated = sampleBankAccountDto()
                .id(createdSecondBankAccount.id())
                .name("Bank 1 Inc.").build();
        //then
        Assertions.assertThrows(BankAccountWithSameNameAlreadyExistsException.class,
                () -> bankAccountService.updateBankAccount(secondBankAccountToBeUpdated.id(), secondBankAccountToBeUpdated));
    }

    @Test
    void shouldThrowExceptionWhenUpdateBankAccountAndAccountWithSpecifiedIdDoesntExist() {
        //given
        BankAccountDto bankAccount = sampleBankAccountDto().id(1L).build();
        //when--then
        Assertions.assertThrows(OperationNotAllowedException.class,
                () -> bankAccountService.updateBankAccount(bankAccount.id(), bankAccount));
    }

    @Test
    void shouldThrowExceptionWhenUpdateBankAccountAndIdsDontEqual() {
        //given
        BankAccountDto bankAccount = sampleBankAccountDto().id(1L).build();
        //when--then
        Assertions.assertThrows(OperationNotAllowedException.class,
                () -> bankAccountService.updateBankAccount(2L, bankAccount));
    }

    @Test
    void shouldDeleteBankAccount() {
        //given
        BankAccountDto newBankAccount = sampleBankAccountDto().id(null).build();
        BankAccountDto createdBankAccount = bankAccountService.createBankAccount(newBankAccount);

        //when
        bankAccountService.deleteBankAccount(createdBankAccount.id());

        //then
        Optional<BankAccountDto> optionalBankAccountDto = bankAccountService.getAccount(createdBankAccount.id());
        Assertions.assertEquals(Optional.empty(), optionalBankAccountDto);
    }

    @Test
    void shouldNotDeleteBankAccountWhenUserIsNotOwner() {
        //given
        User firstUser = sampleUser().username("firstUser").email("firstUser@email.com").build();
        User secondUser = sampleUser().username("secondUser").email("secondUser@email.com").build();
        userProvider.setSignedInUser(firstUser);
        BankAccountDto firstUserBankAccount = bankAccountService.createBankAccount(sampleBankAccountDto().id(null).build());

        //when -- then
        userProvider.setSignedInUser(secondUser);
        Assertions.assertDoesNotThrow(() -> bankAccountService.deleteBankAccount(firstUserBankAccount.id()));

        //when
        userProvider.setSignedInUser(firstUser);
        Optional<BankAccountDto> optionalBankAccountDto = bankAccountService.getAccount(firstUserBankAccount.id());

        //then
        Assertions.assertTrue(optionalBankAccountDto.isPresent());
        Assertions.assertEquals(firstUserBankAccount, optionalBankAccountDto.get());
    }

    @Test
    void shouldNotThrowExceptionWhenDeleteAccountAndAccountDoesntExist() {
        //given--when--then
        Assertions.assertDoesNotThrow(() -> bankAccountService.deleteBankAccount(1L));
    }
}