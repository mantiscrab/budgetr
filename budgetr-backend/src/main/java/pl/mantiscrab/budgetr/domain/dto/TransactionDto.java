package pl.mantiscrab.budgetr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.mantiscrab.budgetr.domain.Money;
import pl.mantiscrab.budgetr.domain.PayeeId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class TransactionDto {
    private final PayeeId payeeId;
    private final LocalDate date;
    private final Money amount;
    private final String description;
    private final List<TransactionComponentDto> components;

    public TransactionDto(PayeeId payeeId, LocalDate date, Money amount, String description, TransactionComponentDto... components) {
        this.payeeId = payeeId;
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.components = List.of(components);
    }

    public List<String> getNewCategories() {
        List<String> names = new ArrayList<>();
        for (TransactionComponentDto component : components) {
            if (component.getCategoryId() == null) {
                names.add(component.getCategoryName());
            }
        }
        return names;
    }

    public TransactionDto withComponents(List<TransactionComponentDto> dtos) {
        return new TransactionDto(this.payeeId, this.date, this.amount, this.description, dtos);
    }
}
