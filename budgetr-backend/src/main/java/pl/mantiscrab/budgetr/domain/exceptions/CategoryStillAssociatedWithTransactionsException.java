package pl.mantiscrab.budgetr.domain.exceptions;

import pl.mantiscrab.budgetr.domain.Category;

public class CategoryStillAssociatedWithTransactionsException extends RuntimeException {
    public CategoryStillAssociatedWithTransactionsException(Category category) {
        super(category + " is still associacted with transactions");
    }
}
