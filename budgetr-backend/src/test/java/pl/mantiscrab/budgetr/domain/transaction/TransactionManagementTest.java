package pl.mantiscrab.budgetr.domain.transaction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import pl.mantiscrab.budgetr.auth.RegistrationHelper;
import pl.mantiscrab.budgetr.auth.dto.UserRegisterDto;
import pl.mantiscrab.budgetr.domain.bankaccount.BankAccountHelper;
import pl.mantiscrab.budgetr.domain.bankaccount.dto.BankAccountDto;
import pl.mantiscrab.budgetr.domain.bankaccount.infrastructure.BankAccountWithLinks;
import pl.mantiscrab.budgetr.domain.transaction.dto.TransactionDto;
import pl.mantiscrab.budgetr.domain.transaction.infrastructure.TransactionHelper;
import pl.mantiscrab.budgetr.domain.transaction.infrastructure.TransactionWithLinks;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static pl.mantiscrab.budgetr.auth.UserAuthTestDataProvider.sampleRegisterDto;
import static pl.mantiscrab.budgetr.domain.bankaccount.BankAccountTestDataProvider.sampleNewBankAccount;
import static pl.mantiscrab.budgetr.domain.transaction.TransactionTestDataProvider.sampleNewTransaction;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
class TransactionManagementTest {
    @LocalServerPort
    private int port;
    @Autowired
    TestRestTemplate restTemplate;

    BankAccountHelper bankAccountHelper;
    RegistrationHelper registrationHelper;
    TransactionHelper transactionHelper;

    final String username = "username";
    final String password = "password";

    @BeforeEach
    void init() {
        bankAccountHelper = new BankAccountHelper(restTemplate, port);
        registrationHelper = new RegistrationHelper(restTemplate, port);
        transactionHelper = new TransactionHelper(restTemplate, port);
    }

    @Test
    @DisplayName("Should create and get accounts")
    void transactionManagement() {
        //given user registers and creates bank account
        register();
        final Long bankAccountId = createBankAccount();

        //when user creates transaction
        final TransactionDto newTransaction = sampleNewTransaction().build();
        final ResponseEntity<TransactionWithLinks> createdTransactionResponse = transactionHelper
                .createTransaction(username, password, bankAccountId, newTransaction);

        //then transaction is created
        Assertions.assertEquals(HttpStatus.CREATED, createdTransactionResponse.getStatusCode());

        //and response matches request
        final TransactionDto createdTransaction = extract(createdTransactionResponse);
        Assertions.assertNotNull(createdTransaction.index());
        assertTransactionResponseEqualsTransactionIgnoreId(createdTransactionResponse, newTransaction);

        //when user request for transaction
        final ResponseEntity<TransactionWithLinks> transactionByBankAccountIdAndIndex = transactionHelper
                .getTransactionByBankAccountIdAndIndex(username, password, bankAccountId, createdTransaction.index());

        //then transaction response matches transaction request
        assertTransactionResponseEqualsTransaction(transactionByBankAccountIdAndIndex, createdTransaction);

        //when user created another transaction
        transactionHelper.createTransaction(username, password, bankAccountId, sampleNewTransaction().build());

        //and user request for transactions
        final ResponseEntity<PagedModel<TransactionWithLinks>> transactions = transactionHelper
                .getTransactionsByBankAccountId(username, password, bankAccountId, null);

        //then there response contains 2 transactions
        Assertions.assertEquals(2, transactions.getBody().getContent().size());
    }

    private void assertTransactionResponseEqualsTransactionIgnoreId(ResponseEntity<TransactionWithLinks> transactionResponse, TransactionDto transaction) {
        final TransactionDto transactionResponseExtracted = extract(transactionResponse);
        Assertions.assertEquals(transaction.amount(), transactionResponseExtracted.amount());
        Assertions.assertEquals(transaction.date(), transactionResponseExtracted.date());
    }

    private void assertTransactionResponseEqualsTransaction(ResponseEntity<TransactionWithLinks> transactionResponse, TransactionDto transaction) {
        final TransactionDto transactionResponseExtracted = extract(transactionResponse);
        Assertions.assertEquals(transaction, transactionResponseExtracted);
    }

    private static TransactionDto extract(ResponseEntity<TransactionWithLinks> transactionResponse) {
        return transactionResponse.getBody().getTransactionDto();
    }

    private Long createBankAccount() {
        final BankAccountDto newBankAccount = sampleNewBankAccount().build();
        final ResponseEntity<BankAccountWithLinks> bankAccount = bankAccountHelper.createAccount(username, password, newBankAccount);
        final Long bankAccountId = bankAccount.getBody().id();
        return bankAccountId;
    }

    private void register() {
        final UserRegisterDto registerDto = sampleRegisterDto().username(username).password(password).build();
        registrationHelper.registerUser(registerDto);
    }
}
