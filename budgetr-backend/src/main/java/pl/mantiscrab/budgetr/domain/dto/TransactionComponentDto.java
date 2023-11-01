package pl.mantiscrab.budgetr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.mantiscrab.budgetr.domain.CategoryId;
import pl.mantiscrab.budgetr.domain.Money;

@AllArgsConstructor
@Getter
public class TransactionComponentDto {
    private final CategoryId categoryId;
    private final String categoryName;
    private final Money amount;
    private final String description;

    public TransactionComponentDto(CategoryId categoryId, Money amount, String description) {
        this.categoryId = categoryId;
        this.categoryName = "";
        this.amount = amount;
        this.description = description;
    }

    public TransactionComponentDto(String categoryName, Money amount, String description) {
        this.categoryId = null;
        this.categoryName = categoryName;
        this.amount = amount;
        this.description = description;
    }

    public boolean hasNewCategory() {
        return categoryId == null;
    }

    public TransactionComponentDto copyWithCategoryId(CategoryId categoryId) {
        return new TransactionComponentDto(
                categoryId,
                this.getAmount(),
                this.getDescription()
        );
    }
}
