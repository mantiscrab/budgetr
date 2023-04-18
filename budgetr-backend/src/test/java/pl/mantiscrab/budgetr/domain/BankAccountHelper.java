package pl.mantiscrab.budgetr.domain;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import pl.mantiscrab.budgetr.UriProvider;
import pl.mantiscrab.budgetr.domain.dto.BankAccountDto;
import pl.mantiscrab.budgetr.domain.infrastructure.BankAccountController;
import pl.mantiscrab.budgetr.domain.infrastructure.BankAccountWithLinks;

import java.net.URI;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

public class BankAccountHelper {
    private final TestRestTemplate restTemplate;
    private final UriProvider uriProvider;
    private final ParameterizedTypeReference<CollectionModel<BankAccountWithLinks>> bankAccountWithLinksCollectionModelTypeReference
            = new ParameterizedTypeReference<>() {
    };

    public BankAccountHelper(TestRestTemplate testRestTemplate, int localServerPort) {
        restTemplate = testRestTemplate;
        uriProvider = new UriProvider(localServerPort);
    }

    public ResponseEntity<BankAccountWithLinks> createAccount(String username, String password, BankAccountDto bankAccountDto) {
        URI uri = uriProvider.getUriOn(on(BankAccountController.class).createBankAccount(null));
        return restTemplate.withBasicAuth(username, password).postForEntity(uri, bankAccountDto, BankAccountWithLinks.class);
    }

    public ResponseEntity<CollectionModel<BankAccountWithLinks>> getAccounts(String username, String password) {
        URI uri = uriProvider.getUriOn(on(BankAccountController.class).getAccounts());
        return restTemplate.withBasicAuth(username, password)
                .exchange(uri, HttpMethod.GET, null, bankAccountWithLinksCollectionModelTypeReference);
    }

    public ResponseEntity<BankAccountWithLinks> getAccount(String username, String password, Long id) {
        URI uri = uriProvider.getUriOn(on(BankAccountController.class).getAccountById(id));
        return restTemplate.withBasicAuth(username, password).getForEntity(uri, BankAccountWithLinks.class);
    }

    public ResponseEntity<BankAccountWithLinks> updateAccount(String username, String password, BankAccountDto bankAccountDto) {
        URI uri = uriProvider.getUriOn(on(BankAccountController.class).updateBankAccount(bankAccountDto.id(), bankAccountDto));
        RequestEntity<BankAccountDto> requestEntity = new RequestEntity<>(bankAccountDto, HttpMethod.PUT, uri);
        return restTemplate.withBasicAuth(username, password).exchange(requestEntity, BankAccountWithLinks.class);
    }

    ResponseEntity<Void> deleteAccount(String username, String password, Long id) {
        URI uri = uriProvider.getUriOn(on(BankAccountController.class).deleteBankAccount(id));
        RequestEntity<Void> requestEntity = new RequestEntity<>(HttpMethod.DELETE, uri);
        return restTemplate.withBasicAuth(username, password).exchange(requestEntity, Void.class);
    }
}
