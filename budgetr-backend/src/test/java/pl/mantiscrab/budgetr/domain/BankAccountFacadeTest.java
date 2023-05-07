package pl.mantiscrab.budgetr.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mantiscrab.budgetr.domain.dto.BankAccountDto;
import pl.mantiscrab.budgetr.domain.exceptions.BankAccountWithSameNameAlreadyExistsException;
import pl.mantiscrab.budgetr.domain.exceptions.OperationNotAllowedException;
import pl.mantiscrab.budgetr.domain.infrastructure.RecentlyAuthenticatedUsersPublisher;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static pl.mantiscrab.budgetr.domain.RecentlyAuthenticatedUserTestDataProvider.sampleUser;

class BankAccountFacadeTest {
    private BankAccountFacade bankAccountFacade;
    private SignedInUsernameProviderMock userProvider;
    private RecentlyAuthenticatedUsersPublisher publisherMock;

    @BeforeEach
    void initializeBankAccountFacade() {
        userProvider = new SignedInUsernameProviderMock("mantiscrab");
        publisherMock = new RecentlyAuthenticatedUsersPublisherMock();
        UserRepository userRepository = new InMemoryUserRepository();
        UserConfig userConfig = new UserConfig(userRepository, userProvider, publisherMock);
        userConfig.userService();
        publisherMock.notify(sampleUser().username("mantiscrab").email("mantiscrab@budgetr.pl").build());
        this.bankAccountFacade = userConfig.bankAccountFacade();
    }

    @Test
    void shouldGetAccountsAssociatedOnlyWithSignedInUser() {
        //given
        publisherMock.notify(sampleUser().username("user1").email("user1@email.com").build());
        publisherMock.notify(sampleUser().username("user2").email("user2@email.com").build());
        userProvider.setSignedInUserName("user1");
        BankAccountDto user1BankAccountNo1 = bankAccountFacade.addBankAccount(BankAccountTestDataProvider.sampleBankAccountDto().index(null).name("User1 Bank Account no.1").build());
        BankAccountDto user1BankAccountNo2 = bankAccountFacade.addBankAccount(BankAccountTestDataProvider.sampleBankAccountDto().index(null).name("User1 Bank Account no.2").build());
        List<BankAccountDto> bankAccountsCreatedByUser1 = List.of(
                user1BankAccountNo1,
                user1BankAccountNo2
        );
        userProvider.setSignedInUserName("user2");
        BankAccountDto user2BankAccountNo1 = bankAccountFacade.addBankAccount(BankAccountTestDataProvider.sampleBankAccountDto().index(null).name("User2 Bank Account no.1").build());
        BankAccountDto user2BankAccountNo2 = bankAccountFacade.addBankAccount(BankAccountTestDataProvider.sampleBankAccountDto().index(null).name("User2 Bank Account no.2").build());
        List<BankAccountDto> bankAccountsCreatedByUser2 = List.of(
                user2BankAccountNo1,
                user2BankAccountNo2
        );
        //when
        userProvider.setSignedInUserName("user1");
        List<BankAccountDto> receivedUser1BankAccounts = bankAccountFacade.getAccounts();
        //then
        Assertions.assertEquals(bankAccountsCreatedByUser1, receivedUser1BankAccounts);

        //when
        userProvider.setSignedInUserName("user2");
        List<BankAccountDto> receivedUser2BankAccounts = bankAccountFacade.getAccounts();
        //then
        Assertions.assertEquals(bankAccountsCreatedByUser2, receivedUser2BankAccounts);
    }

    @Test
    void shouldReturnEmptyOptionalWhenGetAccountAndUserIsNotOwner() {
        //given
        publisherMock.notify(sampleUser().username("user1").email("user1@email.com").build());
        publisherMock.notify(sampleUser().username("user2").email("user2@email.com").build());
        userProvider.setSignedInUserName("user1");
        BankAccountDto createdBankAccount = bankAccountFacade.addBankAccount(
                BankAccountTestDataProvider.sampleBankAccountDto().index(null).build());
        //when
        userProvider.setSignedInUserName("user2");
        //then
        Optional<BankAccountDto> optionalAccount = bankAccountFacade.getAccount(createdBankAccount.index());
        Assertions.assertEquals(Optional.empty(), optionalAccount);
    }

    @Test
    void shouldCreateBankAccount() {
        //given
        BankAccountDto newBankAccount = BankAccountTestDataProvider.sampleBankAccountDto().index(null).build();
        //when
        BankAccountDto createdBankAccount = bankAccountFacade.addBankAccount(newBankAccount);
        //then
        Assertions.assertEquals(newBankAccount.name(), createdBankAccount.name());
        Assertions.assertEquals(newBankAccount.initialBalance(), createdBankAccount.initialBalance());
    }

    @Test
    void shouldThrowExceptionWhenCreateBankAccountAndUserAlreadyHasAccountWithSameName() {
        //given
        BankAccountDto firstBankAccount = BankAccountTestDataProvider.sampleBankAccountDto()
                .index(null)
                .name("Bank Inc.").build();
        bankAccountFacade.addBankAccount(firstBankAccount);
        BankAccountDto secondBankAccountWithSameName = BankAccountTestDataProvider.sampleBankAccountDto()
                .index(null)
                .name("Bank Inc.").build();
        //when--then
        Assertions.assertThrows(BankAccountWithSameNameAlreadyExistsException.class,
                () -> bankAccountFacade.addBankAccount(secondBankAccountWithSameName));
    }

    @Test
    void shouldThrowExceptionWhenCreateBankAccountAndIdNotNull() {
        //given
        BankAccountDto bankAccount = BankAccountTestDataProvider.sampleBankAccountDto().index(1).build();
        //when--then
        Assertions.assertThrows(OperationNotAllowedException.class,
                () -> bankAccountFacade.addBankAccount(bankAccount));
    }

    @Test
    void shouldUpdateBankAccount() {
        //given
        BankAccountDto newBankAccount = BankAccountTestDataProvider.sampleBankAccountDto()
                .index(null)
                .name("Bank Inc.")
                .initialBalance(BigDecimal.ZERO).build();
        BankAccountDto createdBankAccount = bankAccountFacade.addBankAccount(newBankAccount);
        //when
        BankAccountDto bankAccountToBeUpdated = BankAccountTestDataProvider.sampleBankAccountDto()
                .index(createdBankAccount.index())
                .name("Bank Inc.")
                .initialBalance(BigDecimal.ONE).build();
        BankAccountDto updatedBankAccount = bankAccountFacade.updateBankAccount(bankAccountToBeUpdated.index(), bankAccountToBeUpdated);
        //then
        Assertions.assertEquals(bankAccountToBeUpdated, updatedBankAccount);
        //when
        BankAccountDto getUpdatedBankAccount = bankAccountFacade.getAccount(updatedBankAccount.index()).orElseThrow();
        //then
        Assertions.assertEquals(bankAccountToBeUpdated, getUpdatedBankAccount);
    }

    @Test
    void shouldThrowExceptionWhenUpdateBankAccountAndUserIsNotOwner() {
        //given
        publisherMock.notify(sampleUser().username("firstUser").email("firstUser@email.com").build());
        publisherMock.notify(sampleUser().username("secondUser").email("secondUser@email.com").build());
        userProvider.setSignedInUserName("firstUser");
        BankAccountDto firstUserBankAccount = bankAccountFacade.addBankAccount(BankAccountTestDataProvider.sampleBankAccountDto().index(null).build());

        //when--then
        userProvider.setSignedInUserName("secondUser");
        Assertions.assertThrows(OperationNotAllowedException.class, () -> bankAccountFacade.updateBankAccount(firstUserBankAccount.index(), firstUserBankAccount));
    }

    @Test
    void shouldThrowExceptionWhenUpdateBankAccountAndAccountWithSameNameAlreadyExists() {
        //given
        BankAccountDto firstBankAccount = BankAccountTestDataProvider.sampleBankAccountDto()
                .index(null)
                .name("Bank 1 Inc.").build();
        BankAccountDto secondBankAccount = BankAccountTestDataProvider.sampleBankAccountDto()
                .index(null)
                .name("Bank 2 Inc.").build();
        bankAccountFacade.addBankAccount(firstBankAccount);
        BankAccountDto createdSecondBankAccount = bankAccountFacade.addBankAccount(secondBankAccount);
        //when--then
        BankAccountDto secondBankAccountToBeUpdated = BankAccountTestDataProvider.sampleBankAccountDto()
                .index(createdSecondBankAccount.index())
                .name("Bank 1 Inc.").build();
        //then
        Assertions.assertThrows(BankAccountWithSameNameAlreadyExistsException.class,
                () -> bankAccountFacade.updateBankAccount(secondBankAccountToBeUpdated.index(), secondBankAccountToBeUpdated));
    }

    @Test
    void shouldThrowExceptionWhenUpdateBankAccountAndAccountWithSpecifiedIdDoesntExist() {
        //given
        BankAccountDto bankAccount = BankAccountTestDataProvider.sampleBankAccountDto().index(1).build();
        //when--then
        Assertions.assertThrows(OperationNotAllowedException.class,
                () -> bankAccountFacade.updateBankAccount(bankAccount.index(), bankAccount));
    }

    @Test
    void shouldThrowExceptionWhenUpdateBankAccountAndIdsDontEqual() {
        //given
        BankAccountDto bankAccount = BankAccountTestDataProvider.sampleBankAccountDto().index(1).build();
        //when--then
        Assertions.assertThrows(OperationNotAllowedException.class,
                () -> bankAccountFacade.updateBankAccount(2, bankAccount));
    }

    @Test
    void shouldDeleteBankAccount() {
        //given
        BankAccountDto newBankAccount = BankAccountTestDataProvider.sampleBankAccountDto().index(null).build();
        BankAccountDto createdBankAccount = bankAccountFacade.addBankAccount(newBankAccount);

        //when
        bankAccountFacade.deleteBankAccount(createdBankAccount.index());

        //then
        Optional<BankAccountDto> optionalBankAccountDto = bankAccountFacade.getAccount(createdBankAccount.index());
        Assertions.assertEquals(Optional.empty(), optionalBankAccountDto);
    }

    @Test
    void shouldNotDeleteBankAccountWhenUserIsNotOwner() {
        //given
        publisherMock.notify(sampleUser().username("firstUser").email("firstUser@email.com").build());
        publisherMock.notify(sampleUser().username("secondUser").email("secondUser@email.com").build());

        userProvider.setSignedInUserName("firstUser");
        BankAccountDto firstUserBankAccount = bankAccountFacade.addBankAccount(BankAccountTestDataProvider.sampleBankAccountDto().index(null).build());

        //when -- then
        userProvider.setSignedInUserName("secondUser");
        Assertions.assertDoesNotThrow(() -> bankAccountFacade.deleteBankAccount(firstUserBankAccount.index()));

        //when
        userProvider.setSignedInUserName("firstUser");
        Optional<BankAccountDto> optionalBankAccountDto = bankAccountFacade.getAccount(firstUserBankAccount.index());

        //then
        Assertions.assertTrue(optionalBankAccountDto.isPresent());
        Assertions.assertEquals(firstUserBankAccount, optionalBankAccountDto.get());
    }

    @Test
    void shouldNotThrowExceptionWhenDeleteAccountAndAccountNonexistent() {
        //given--when--then
        Assertions.assertDoesNotThrow(() -> bankAccountFacade.deleteBankAccount(1));
    }
}