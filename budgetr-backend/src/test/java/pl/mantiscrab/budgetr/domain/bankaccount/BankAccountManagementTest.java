package pl.mantiscrab.budgetr.domain.bankaccount;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
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
    }

    private void assertBankAccountResponseMatchesRequestIgnoreId(ResponseEntity<BankAccountDto> response, BankAccountDto requestBody) {
        BankAccountDto responseBody = response.getBody();
        Assertions.assertEquals(responseBody.name(), requestBody.name());
        Assertions.assertEquals(responseBody.initialBalance(), requestBody.initialBalance());
    }
}
