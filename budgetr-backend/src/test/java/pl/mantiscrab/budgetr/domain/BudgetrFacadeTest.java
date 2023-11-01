package pl.mantiscrab.budgetr.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mantiscrab.budgetr.domain.dto.BankAccountDto;
import pl.mantiscrab.budgetr.domain.dto.CategoryDto;
import pl.mantiscrab.budgetr.domain.dto.TransactionComponentDto;
import pl.mantiscrab.budgetr.domain.dto.TransactionDto;
import pl.mantiscrab.budgetr.domain.exceptions.BankAccountAlreadyExistsException;
import pl.mantiscrab.budgetr.domain.exceptions.CategoryStillAssociatedWithTransactionsException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

class BudgetrFacadeTest {
    private BudgetrFacade facade;

    @BeforeEach
    void init() {
        facade = new BudgetrFacade(new BankAccountRepository(), new CategoryRepository());
    }

    @Test
    void canAddCategory() {
        //given
        CategoryId id = anyCategory();
        CategoryDto categoryDto = facade.findCategory(id).orElseThrow();

        //expect
        Assertions.assertNotNull(id);
        Assertions.assertEquals(id, categoryDto.getId());
        Assertions.assertEquals("Kategoria", categoryDto.getName());
    }

    @Test
    void cannotAddCategory() {
        //given
        anyCategory();

        //expect
        Assertions.assertThrows(CategoryAlreadyExistsException.class,
                () -> anyCategory());
    }

    @Test
    void canDeleteCategory() {
        //given
        CategoryId categoryId = anyCategory();

        //when
        facade.deleteCategory(categoryId);

        //then
        Optional<CategoryDto> category = facade.findCategory(categoryId);
        Assertions.assertEquals(Optional.empty(), category);
    }

    @Test
    void cannotDeleteCategoryWhenAnyTransactionAssociated() {
        //given
        CategoryId catId = anyCategory();
        TransactionDto t1 = transactionDto(catId);
        addBankAccount(BankAccountId.of("MLNM"));

        //when
        facade.withdraw(BankAccountId.of("MLNM"), t1);

        //then
        Assertions.assertThrows(CategoryStillAssociatedWithTransactionsException.class,
                () -> facade.deleteCategory(catId));
    }

    @Test
    void canDeleteCategoryAndReplaceWithNewOne() {
        //given
        facade.addBankAccount(BankAccountDto.of("MLNM", Money.of("100")));
        CategoryId oldCatId = facade.addCategory("Old");
        TransactionDto t1 = transactionDto(oldCatId, Money.of("20"));

        //when
        TransactionId id = facade.withdraw(BankAccountId.of("MLNM"), t1);

        //then
        boolean isAssociated = facade.isAnyTransactionAssociated(oldCatId);
        Assertions.assertTrue(isAssociated);

        //when
        CategoryId newCatId = facade.addCategory("New");
        facade.replaceAndDeleteCategory(oldCatId, newCatId);

        //then
        Assertions.assertTrue(facade.findCategoryByName("Old").isEmpty());
        TransactionDto transaction = facade.getTransaction(BankAccountId.of("MLNM"), id).orElseThrow();
        Assertions.assertTrue(transaction.getComponents().stream()
                .allMatch(t -> t.getCategoryId().equals(newCatId)));
    }

    @Test
    void canAddBankAccount() {
        //given
        facade.addBankAccount(BankAccountDto.of("MLNM", Money.of("100")));

        //when
        BankAccount bankAccount = facade.findBankAccount(BankAccountId.of("MLNM")).orElseThrow();

        //then
        Assertions.assertEquals(BankAccountId.of("MLNM"), bankAccount.getName());
        Assertions.assertEquals(Money.of("100"), bankAccount.getInitialBalance());
        Assertions.assertEquals(Money.of("100"), bankAccount.getBalance());
    }

    @Test
    void cannotAddBankAccount() {
        //given
        facade.addBankAccount(BankAccountDto.of("MLNM", Money.of("100")));

        //expect
        Assertions.assertThrows(BankAccountAlreadyExistsException.class,
                () -> facade.addBankAccount(BankAccountDto.of("MLNM", Money.of("100"))));
    }

    @Test
    void accountBalanceChanges() {
        //given
        CategoryId cat = anyCategory();
        BankAccountId mlnm = BankAccountId.of("MLNM");

        addBankAccount(mlnm);
        TransactionDto t1 = transactionDto(cat, Money.of("20"));

        //when
        facade.withdraw(mlnm, t1);

        //then
        BankAccount ba = facade.findBankAccount(mlnm).orElseThrow();
        Assertions.assertEquals(Money.of("80"), ba.getBalance());

        //when
        Money amount = Money.of("7");
        TransactionDto t2 = transactionDto(cat, amount);
        facade.deposit(mlnm, t2);

        //then
        Assertions.assertEquals(Money.of("87"), ba.getBalance());
    }

    @Test
    void canAddTransactionWithNewCategory() {
        //given
        BankAccountId mlnm = BankAccountId.of("MLNM");
        addBankAccount(mlnm);
        TransactionDto t1 = sampleTransaction()
                .amount(Money.of("30"))
                .components(List.of(
                        new TransactionComponentDto("New Category", Money.of("20"), null),
                        new TransactionComponentDto("New Category", Money.of("10"), null))).build();

        //when
        TransactionId id = facade.withdraw(mlnm, t1);

        //then
        TransactionDto transaction = facade.getTransaction(mlnm, id).orElseThrow();

        Assertions.assertTrue(transaction.getComponents().stream().allMatch(
                t -> t.getCategoryName().equals("New Category")));
        Assertions.assertNotNull(transaction.getComponents().stream().findFirst().orElseThrow().getCategoryId());

    }

    private void addBankAccount(BankAccountId mlnm) {
        facade.addBankAccount(BankAccountDto.of(mlnm, Money.of("100")));
    }

    private CategoryId anyCategory() {
        return facade.addCategory("Kategoria");
    }

    private static TransactionDto.TransactionDtoBuilder sampleTransaction() {
        return TransactionDto.builder().payeeId(new PayeeId("SKLEP")).date(LocalDate.of(2023, 1, 1)).description(null);
    }

    private static TransactionDto transactionDto(CategoryId cat) {
        Money amount = Money.of("20");
        return transactionDto(cat, amount);
    }

    private static TransactionDto transactionDto(CategoryId cat, Money amount) {
        return TransactionDto.builder()
                .payeeId(new PayeeId("SKLEP"))
                .date(LocalDate.of(2023, 1, 1))
                .amount(amount)
                .description(null)
                .components(List.of(
                        new TransactionComponentDto(cat, amount, null)
                ))
                .build();
    }
}