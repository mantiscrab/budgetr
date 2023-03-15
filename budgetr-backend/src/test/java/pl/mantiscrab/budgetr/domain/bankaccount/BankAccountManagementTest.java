package pl.mantiscrab.budgetr.domain.bankaccount;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
//import pl.mantiscrab.budgetr.auth.RegistrationHelper;
//import pl.mantiscrab.budgetr.auth.dto.UserRegisterDto;
import pl.mantiscrab.budgetr.domain.bankaccount.dto.BankAccountDto;

import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
//import static pl.mantiscrab.budgetr.auth.UserAuthTestDataProvider.sampleRegisterDto;
import static pl.mantiscrab.budgetr.domain.bankaccount.BankAccountTestDataProvider.sampleBankAccountDto;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@Disabled
class BankAccountManagementTest {
    @LocalServerPort
    private int port;
    @Autowired
    TestRestTemplate restTemplate;
//    RegistrationHelper registrationHelper;
    BankAccountHelper bankAccountHelper;

    @BeforeEach
    void init() {
        bankAccountHelper = new BankAccountHelper(restTemplate, port);
//        registrationHelper = new RegistrationHelper(restTemplate, port);
    }

    @Test
    @DisplayName("Should create two accounts and return them")
    void bankAccountManagement() {
        //when user registers
        String username = "mantiscrab";
        String password = "password";
//        UserRegisterDto registerDto = sampleRegisterDto()
//                .username(username)
//                .password(password)
//                .email("mantiscrab@budgetr.com").build();
//        registrationHelper.registerUser(registerDto);
        //and user creates account
        BankAccountDto createFirstAccountRequestBody = sampleBankAccountDto().id(null).name("Bank 1").build();
        ResponseEntity<BankAccountDto> createFirstAccountResponse = bankAccountHelper
                .createAccount(username, password, createFirstAccountRequestBody);
        //then response matches request
        assertBankAccountResponseMatchesRequestIgnoreId(createFirstAccountResponse, createFirstAccountRequestBody);

        //when user request for created account
        ResponseEntity<BankAccountDto> getFirstAccountResponse = bankAccountHelper
                .getAccount(username, password, createFirstAccountResponse.getBody().id());
        //then response matches request
        assertBankAccountResponseMatchesRequestIgnoreId(getFirstAccountResponse, createFirstAccountRequestBody);

        //when user creates second account
        BankAccountDto createSecondAccountRequestBody = sampleBankAccountDto().id(null).name("Bank 2").build();
        ResponseEntity<BankAccountDto> createSecondAccountResponse = bankAccountHelper
                .createAccount(username, password, createSecondAccountRequestBody);
        //then response matches request
        assertBankAccountResponseMatchesRequestIgnoreId(createSecondAccountResponse, createSecondAccountRequestBody);

        //when user request for accounts
        ResponseEntity<List<BankAccountDto>> getAccountsResponse = bankAccountHelper.getAccounts(username, password);
        //then response contains two accounts
        Assertions.assertEquals(2, getAccountsResponse.getBody().size());

        //when user updates account
        BankAccountDto updateSecondAccountRequestBody = sampleBankAccountDto()
                .id(createSecondAccountResponse.getBody().id())
                .name("Updated Bank 2")
                .build();
        ResponseEntity<BankAccountDto> updateSecondAccountResponse = bankAccountHelper
                .updateAccount(username, password, updateSecondAccountRequestBody);

        //then response matches request
        assertBankAccountResponseMatchesRequest(updateSecondAccountResponse, updateSecondAccountRequestBody);

        //when user request for updated account
        ResponseEntity<BankAccountDto> getUpdatedSecondBankAccountResponse = bankAccountHelper
                .getAccount(username, password, updateSecondAccountRequestBody.id());

        //then response matches request
        assertBankAccountResponseMatchesRequest(getUpdatedSecondBankAccountResponse, updateSecondAccountRequestBody);

        //when user deletes second account
        ResponseEntity<Void> deleteSecondAccountResponse = bankAccountHelper.deleteAccount(username, password, getUpdatedSecondBankAccountResponse.getBody().id());

        //then response status is NO_CONTENT
        Assertions.assertEquals(HttpStatus.NO_CONTENT, deleteSecondAccountResponse.getStatusCode());
    }

    private void assertBankAccountResponseMatchesRequestIgnoreId(ResponseEntity<BankAccountDto> response, BankAccountDto requestBody) {
        BankAccountDto responseBody = response.getBody();
        Assertions.assertEquals(responseBody.name(), requestBody.name());
        Assertions.assertEquals(responseBody.initialBalance(), requestBody.initialBalance());
    }

    private void assertBankAccountResponseMatchesRequest(ResponseEntity<BankAccountDto> response, BankAccountDto requestBody) {
        BankAccountDto responseBody = response.getBody();
        Assertions.assertEquals(responseBody.id(), requestBody.id());
        Assertions.assertEquals(responseBody.name(), requestBody.name());
        Assertions.assertEquals(responseBody.initialBalance(), requestBody.initialBalance());
    }
}
