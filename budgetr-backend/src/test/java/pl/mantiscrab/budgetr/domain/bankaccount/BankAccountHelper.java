package pl.mantiscrab.budgetr.domain.bankaccount;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pl.mantiscrab.budgetr.UriProvider;
import pl.mantiscrab.budgetr.domain.bankaccount.dto.BankAccountDto;

import java.net.URI;
import java.util.List;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

public class BankAccountHelper {
    private final TestRestTemplate restTemplate;
    private final UriProvider uriProvider;
    private final ParameterizedTypeReference<List<BankAccountDto>> typeReference = new ParameterizedTypeReference<>() {
    };

    public BankAccountHelper(TestRestTemplate testRestTemplate, int localServerPort) {
        restTemplate = testRestTemplate;
        uriProvider = new UriProvider(localServerPort);
    }

    public ResponseEntity<BankAccountDto> createAccount(String username, String password, BankAccountDto bankAccountDto) {
        URI uri = uriProvider.getUriOn(on(BankAccountController.class).createBankAccount(null));
        return restTemplate.withBasicAuth(username, password).postForEntity(uri, bankAccountDto, BankAccountDto.class);
    }

    public ResponseEntity<List<BankAccountDto>> getAccounts(String username, String password) {
        URI uri = uriProvider.getUriOn(on(BankAccountController.class).getAccounts());
        return restTemplate.withBasicAuth(username, password)
                .exchange(uri, HttpMethod.GET, null, typeReference);
    }

    public ResponseEntity<BankAccountDto> getAccount(String username, String password, Long id) {
        URI uri = uriProvider.getUriOn(on(BankAccountController.class).getAccountById(id));
        return restTemplate.withBasicAuth(username, password).getForEntity(uri, BankAccountDto.class);
    }
}