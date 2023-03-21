package pl.mantiscrab.budgetr.domain.transaction.infrastructure;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pl.mantiscrab.budgetr.UriProvider;
import pl.mantiscrab.budgetr.domain.transaction.dto.TransactionDto;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.MvcLink.on;

public class TransactionHelper {
    private final TestRestTemplate restTemplate;
    private final UriProvider uriProvider;
    private final ParameterizedTypeReference<PagedModel<TransactionWithLinks>> transactionsWithLinksTypeReference
            = new ParameterizedTypeReference<>() {
    };

    public TransactionHelper(TestRestTemplate testRestTemplate, int localServerPort) {
        restTemplate = testRestTemplate;
        uriProvider = new UriProvider(localServerPort);
    }

    public ResponseEntity<TransactionWithLinks> createTransaction(String username, String password, Long bankAccountId, TransactionDto transactionDto) {
        final URI uri = uriProvider.getUriOn(on(TransactionController.class).createTransaction(bankAccountId, null));
        return restTemplate.withBasicAuth(username, password).postForEntity(uri, transactionDto, TransactionWithLinks.class);
    }

    public ResponseEntity<TransactionWithLinks> getTransactionByBankAccountIdAndIndex(String username, String password, Long bankAccountId, Integer index) {
        final URI uri = uriProvider.getUriOn(on(TransactionController.class).getTransactionByBankAccountIdAndIndex(bankAccountId, index));
        return restTemplate.withBasicAuth(username, password).getForEntity(uri, TransactionWithLinks.class);
    }

    public ResponseEntity<PagedModel<TransactionWithLinks>> getTransactionsByBankAccountId(String username, String password, Long bankAccountId, Pageable pageable) {
        final URI uri = uriProvider.getUriOn(on(TransactionController.class).getTransactionsByBankAccountId(bankAccountId, pageable, null));
        return restTemplate.withBasicAuth(username, password).exchange(uri, HttpMethod.GET, null, transactionsWithLinksTypeReference);
    }
}
