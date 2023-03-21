package pl.mantiscrab.budgetr.domain.bankaccount;

import pl.mantiscrab.budgetr.domain.bankaccount.dto.BankAccountDto;

import java.math.BigDecimal;

public class BankAccountTestDataProvider {
    private static final Long ID = 1L;
    private static final String NAME = "Budgetr Bank Inc.";
    private static final BigDecimal INITIAL_BALANCE = new BigDecimal("0.00");

    public static BankAccountDto.BankAccountDtoBuilder sampleBankAccountDto() {
        return BankAccountDto.builder()
                .id(ID)
                .name(NAME)
                .initialBalance(INITIAL_BALANCE);
    }

    public static BankAccountDto.BankAccountDtoBuilder sampleNewBankAccount() {
        return BankAccountDto.builder()
                .id(null)
                .name(NAME)
                .initialBalance(INITIAL_BALANCE);
    }
    
}
