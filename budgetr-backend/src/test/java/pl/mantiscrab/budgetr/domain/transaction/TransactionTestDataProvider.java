package pl.mantiscrab.budgetr.domain.transaction;

import pl.mantiscrab.budgetr.domain.transaction.dto.TransactionDto;

import java.math.BigDecimal;
import java.time.LocalDate;

class TransactionTestDataProvider {

    private static final BigDecimal AMOUNT = BigDecimal.ONE;
    private static final LocalDate DATE = LocalDate.of(2023, 1, 1);
    private static final int INDEX = 1;

    public static TransactionDto.TransactionDtoBuilder sampleTransaction() {
        return TransactionDto.builder()
                .index(INDEX)
                .amount(AMOUNT)
                .date(DATE);
    }

    public static TransactionDto.TransactionDtoBuilder sampleNewTransaction() {
        return TransactionDto.builder()
                .index(null)
                .amount(AMOUNT)
                .date(DATE);
    }
}
