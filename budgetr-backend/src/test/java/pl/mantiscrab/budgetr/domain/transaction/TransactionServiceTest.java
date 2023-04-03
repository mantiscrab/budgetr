package pl.mantiscrab.budgetr.domain.transaction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.mantiscrab.budgetr.domain.bankaccount.BankAccountService;
import pl.mantiscrab.budgetr.domain.bankaccount.dto.BankAccountDto;
import pl.mantiscrab.budgetr.domain.exceptions.OperationNotAllowedException;
import pl.mantiscrab.budgetr.domain.transaction.dto.TransactionDto;
import pl.mantiscrab.budgetr.domain.transaction.exceptions.TransactionAmountIsNegativeException;
import pl.mantiscrab.budgetr.domain.user.DummySignedInUserProvider;
import pl.mantiscrab.budgetr.domain.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;

import static pl.mantiscrab.budgetr.domain.bankaccount.BankAccountTestDataProvider.sampleNewBankAccount;
import static pl.mantiscrab.budgetr.domain.transaction.TransactionTestDataProvider.sampleNewTransaction;
import static pl.mantiscrab.budgetr.domain.transaction.TransactionTestDataProvider.sampleTransaction;
import static pl.mantiscrab.budgetr.domain.user.UserTestDataProvider.sampleUser;


class TransactionServiceTest {
    private BankAccountService bankAccountService;
    private DummySignedInUserProvider userProvider;
    private TransactionService transactionService;
    private TransactionTestConfig transactionTestConfig = new TransactionTestConfig();

    @BeforeEach
    void initTransactionService() {
        User signedInUser = sampleUser().username("mantiscrab").email("mantiscrab@budgetr.pl").build();
        this.userProvider = new DummySignedInUserProvider(signedInUser);
        this.transactionTestConfig = new TransactionTestConfig();
        this.bankAccountService = transactionTestConfig.bankAccountService(this.userProvider);
        this.transactionService = transactionTestConfig.transactionService(this.bankAccountService);
    }

    @Test
    void shouldCreateTransaction() {
        //given
        final BankAccountDto newBankAccount = sampleNewBankAccount().build();
        final BankAccountDto bankAccount = bankAccountService.createBankAccount(newBankAccount);
        //when
        final TransactionDto newTransaction = sampleNewTransaction().build();
        final TransactionDto createdTransaction =
                transactionService.createTransaction(bankAccount.id(), newTransaction);
        //then
        Assertions.assertNotNull(createdTransaction.index());
        Assertions.assertEquals(newTransaction.amount(), createdTransaction.amount());
        Assertions.assertEquals(newTransaction.date(), createdTransaction.date());
    }

    @Test
    void shouldThrowExceptionWhenCreateTransactionAndIndexNotNull() {
        //given
        final BankAccountDto newBankAccount = sampleNewBankAccount().build();
        final BankAccountDto bankAccount = bankAccountService.createBankAccount(newBankAccount);
        final TransactionDto newTransaction = sampleTransaction().index(1).build();
        //when -- then
        Assertions.assertThrows(OperationNotAllowedException.class,
                () -> transactionService.createTransaction(bankAccount.id(), newTransaction));
    }

    @Test
    void shouldThrowExceptionWhenCreateTransactionAndAmountIsNegative() {
        //given
        final BankAccountDto bankAccount = bankAccountService.createBankAccount(sampleNewBankAccount().build());
        final TransactionDto newTransaction = sampleNewTransaction().amount(new BigDecimal("-1")).build();
        //when -- then
        Assertions.assertThrows(TransactionAmountIsNegativeException.class,
                () -> transactionService.createTransaction(bankAccount.id(), newTransaction));
    }

    @Test
    @DisplayName("Should return different transactions (based on related bank account)" +
            "even when they have same index")
    void shouldReturnDifferentTransactionsBasedOnAccountEvenWhenTheyHaveSameIndex() {
        //given
        final BankAccountDto ba1 = bankAccountService.createBankAccount(sampleNewBankAccount().name("ba1").build());
        final TransactionDto ba1t1 = transactionService.createTransaction(ba1.id(),
                sampleNewTransaction().date(LocalDate.of(1111, 1, 1)).build());
        final BankAccountDto ba2 =
                bankAccountService.createBankAccount(sampleNewBankAccount().name("ba2").build());
        final TransactionDto ba2t1 = transactionService.createTransaction(ba2.id(),
                sampleNewTransaction().date(LocalDate.of(2222, 2, 2)).build());

        //when
        final TransactionDto ba1t1Get = transactionService.getTransactionByBankAccountIdAndIndex(ba1.id(), ba1t1.index()).orElseThrow();
        final TransactionDto ba2t1Get = transactionService.getTransactionByBankAccountIdAndIndex(ba2.id(), ba2t1.index()).orElseThrow();

        //then
        Assertions.assertEquals(ba1t1Get.index(), ba2t1Get.index());
        Assertions.assertNotEquals(ba1t1Get, ba2t1Get);
    }

    @Test
    @DisplayName("Should return different transactions (based on related user and bank account)" +
            "even when they have same index")
    void shouldReturnDifferentTransactionsBasedOnUserAndAccountEvenWhenTheyHaveSameIndex() {
        //given
        final User u1 = sampleUser().username("u1").build();
        userProvider.setSignedInUser(u1);
        final BankAccountDto u1ba1 = bankAccountService.createBankAccount(sampleNewBankAccount().name("ba1").build());
        final TransactionDto u1ba1t1 = transactionService.createTransaction(u1ba1.id(),
                sampleNewTransaction().date(LocalDate.of(1111, 1, 1)).build());

        final User u2 = sampleUser().username("u2").build();
        userProvider.setSignedInUser(u2);
        final BankAccountDto u2ba1 = bankAccountService.createBankAccount(sampleNewBankAccount().name("ba1").build());
        final TransactionDto u2ba1t1 = transactionService.createTransaction(u2ba1.id(),
                sampleNewTransaction().date(LocalDate.of(2222, 2, 2)).build());

        //when
        userProvider.setSignedInUser(u1);
        final TransactionDto u1ba1t1Get = transactionService.getTransactionByBankAccountIdAndIndex(u1ba1.id(), u1ba1t1.index()).orElseThrow();
        userProvider.setSignedInUser(u2);
        final TransactionDto u2ba1t1Get = transactionService.getTransactionByBankAccountIdAndIndex(u2ba1.id(), u2ba1t1.index()).orElseThrow();

        //then
        Assertions.assertEquals(u1ba1t1.index(), u2ba1t1.index());
        Assertions.assertNotEquals(u1ba1t1Get, u2ba1t1Get);
    }
}