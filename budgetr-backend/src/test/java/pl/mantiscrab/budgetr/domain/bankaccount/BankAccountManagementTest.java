package pl.mantiscrab.budgetr.domain.bankaccount;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import pl.mantiscrab.budgetr.auth.RegistrationHelper;
import pl.mantiscrab.budgetr.auth.dto.UserRegisterDto;
import pl.mantiscrab.budgetr.domain.bankaccount.dto.BankAccountDto;

import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static pl.mantiscrab.budgetr.auth.UserAuthTestDataProvider.sampleRegisterDto;
import static pl.mantiscrab.budgetr.domain.bankaccount.BankAccountTestDataProvider.sampleBankAccountDto;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
class BankAccountManagementTest {
    @LocalServerPort
    private int port;
    @Autowired
    TestRestTemplate restTemplate;
    RegistrationHelper registrationHelper;
    BankAccountHelper bankAccountHelper;

    @BeforeEach
    void init() {
        bankAccountHelper = new BankAccountHelper(restTemplate, port);
        registrationHelper = new RegistrationHelper(restTemplate, port);
    }

    @Test
    @DisplayName("Should create two accounts and return them")
    void bankAccountManagement() {
        //when user registers
        String username = "mantiscrab";
        String password = "password";
        UserRegisterDto registerDto = sampleRegisterDto()
                .username(username)
                .password(password)
                .email("mantiscrab@budgetr.com").build();
        registrationHelper.registerUser(registerDto);
        //and user creates account
        BankAccountDto firstCreateAccountRequestBody = sampleBankAccountDto().id(null).name("Bank 1").build();
        ResponseEntity<BankAccountDto> firstCreateAccountResponse = bankAccountHelper
                .createAccount(username, password, firstCreateAccountRequestBody);
        //then response matches request
        assertBankAccountResponseMatchesRequestIgnoreId(firstCreateAccountResponse, firstCreateAccountRequestBody);

        //when user request for created account
        ResponseEntity<BankAccountDto> firstGetAccountResponse = bankAccountHelper
                .getAccount(username, password, firstCreateAccountResponse.getBody().id());
        //then response matches request
        assertBankAccountResponseMatchesRequestIgnoreId(firstGetAccountResponse, firstCreateAccountRequestBody);

        //when user creates second account
        BankAccountDto secondCreateAccountRequestBody = sampleBankAccountDto().id(null).name("Bank 2").build();
        ResponseEntity<BankAccountDto> secondResponse = bankAccountHelper
                .createAccount(username, password, secondCreateAccountRequestBody);
        //then response matches request
        assertBankAccountResponseMatchesRequestIgnoreId(secondResponse, secondCreateAccountRequestBody);

        //when user request for accounts
        ResponseEntity<List<BankAccountDto>> accountsResponse = bankAccountHelper.getAccounts(username, password);
        //then response contains two accounts
        Assertions.assertEquals(2, accountsResponse.getBody().size());

        //when user updates account
        BankAccountDto updateSecondBankAccountRequestBody = sampleBankAccountDto()
                .id(secondResponse.getBody().id())
                .name("Updated Bank 2")
                .build();
        ResponseEntity<BankAccountDto> updateSecondBankAccountResponse = bankAccountHelper
                .updateAccount(username, password, updateSecondBankAccountRequestBody);

        //then response matches request
        assertBankAccountResponseMatchesRequest(updateSecondBankAccountResponse, updateSecondBankAccountRequestBody);

        //when user request for updated account
        ResponseEntity<BankAccountDto> getUpdatedSecondBankAccountResponse = bankAccountHelper
                .getAccount(username, password, updateSecondBankAccountRequestBody.id());

        //then response matches request
        assertBankAccountResponseMatchesRequest(getUpdatedSecondBankAccountResponse, updateSecondBankAccountRequestBody);
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
