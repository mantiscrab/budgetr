package pl.mantiscrab.budgetr.domain.transaction;

import pl.mantiscrab.budgetr.domain.user.DummySignedInUserProvider;

class TransactionTestConfig {
    static TransactionService transactionService(DummySignedInUserProvider userProvider) {
        return null;
    }
}
