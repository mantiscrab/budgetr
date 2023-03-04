package pl.mantiscrab.budgetr.domain.bankaccount;

import pl.mantiscrab.budgetr.domain.bankaccount.dto.BankAccountDto;

import java.math.BigDecimal;

class BankAccountTestDataProvider {
    private static final Long ID = 1L;
    private static final String NAME = "Budgetr Bank Inc.";
    private static final BigDecimal INITIAL_BALANCE = new BigDecimal("0");

    static BankAccountDto.BankAccountDtoBuilder sampleBankAccountDto() {
        return BankAccountDto.builder().id(ID).name(NAME).initialBalance(INITIAL_BALANCE);
    }
    
}