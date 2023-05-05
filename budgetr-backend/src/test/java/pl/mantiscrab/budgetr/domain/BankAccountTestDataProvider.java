package pl.mantiscrab.budgetr.domain;

import pl.mantiscrab.budgetr.domain.dto.BankAccountDto;

import java.math.BigDecimal;

class BankAccountTestDataProvider {
    private static final int INDEX = 0;
    private static final String NAME = "Budgetr Bank Inc.";
    private static final BigDecimal INITIAL_BALANCE = new BigDecimal("0.00");

    static BankAccountDto.BankAccountDtoBuilder sampleBankAccountDto() {
        return BankAccountDto.builder()
                .index(INDEX)
                .name(NAME)
                .initialBalance(INITIAL_BALANCE);
    }

}
