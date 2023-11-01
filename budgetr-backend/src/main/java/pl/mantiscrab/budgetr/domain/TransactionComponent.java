package pl.mantiscrab.budgetr.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
class TransactionComponent {
    private CategoryId category;
    private final Money amount;
    private final String description;

    boolean isAssociatedWith(CategoryId catId) {
        return catId.equals(category);
    }

    void replaceCategory(CategoryId oldCatId, CategoryId newCatId) {
        if (category.equals(oldCatId))
            this.category = newCatId;
    }
}
