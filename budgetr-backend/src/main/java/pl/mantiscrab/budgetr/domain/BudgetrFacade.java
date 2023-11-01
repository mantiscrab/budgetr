package pl.mantiscrab.budgetr.domain;

import lombok.AllArgsConstructor;
import pl.mantiscrab.budgetr.domain.dto.BankAccountDto;
import pl.mantiscrab.budgetr.domain.dto.CategoryDto;
import pl.mantiscrab.budgetr.domain.dto.TransactionComponentDto;
import pl.mantiscrab.budgetr.domain.dto.TransactionDto;
import pl.mantiscrab.budgetr.domain.exceptions.BankAccountAlreadyExistsException;
import pl.mantiscrab.budgetr.domain.exceptions.BankAccountNotFoundException;
import pl.mantiscrab.budgetr.domain.exceptions.CategoryStillAssociatedWithTransactionsException;

import java.util.*;

@AllArgsConstructor
class BudgetrFacade {
    private final BankAccountRepository bankAccountRepository;
    private final CategoryRepository categoryRepository;

    void addBankAccount(BankAccountDto bankAccountDto) {
        BankAccountId id = bankAccountDto.id();
        if (bankAccountRepository.findBankAccount(id).isPresent()) {
            throw new BankAccountAlreadyExistsException(id);
        }
        BankAccount bankAccount = new BankAccount(bankAccountDto.id(), bankAccountDto.initialBalance());
        bankAccountRepository.save(bankAccount);
    }

    Optional<BankAccount> findBankAccount(BankAccountId id) {
        return bankAccountRepository.findBankAccount(id);
    }

//    void withdraw(BankAccountId bankAccountId, TransactionDto transactionDto) {
//        BankAccount bankAccount = bankAccountRepository.findBankAccount(bankAccountId)
//                .orElseThrow(() -> new BankAccountNotFoundException(bankAccountId));
//        Transaction transaction = mapTransactionFromTransactionDto(transactionDto);
//        bankAccount.addTransaction(transaction);
//    }

    TransactionId withdraw(BankAccountId bankAccountId, TransactionDto transactionDto) {
        BankAccount bankAccount = bankAccountRepository.findBankAccount(bankAccountId)
                .orElseThrow(() -> new BankAccountNotFoundException(bankAccountId));

        TransactionDto dto = resolveCategories(transactionDto);

        Transaction transaction = withdrawalTransactionFromTransactionDto(dto);
        return bankAccount.addTransaction(transaction);
    }

    TransactionId deposit(BankAccountId bankAccountId, TransactionDto transactionDto) {
        BankAccount bankAccount = bankAccountRepository.findBankAccount(bankAccountId)
                .orElseThrow(() -> new BankAccountNotFoundException(bankAccountId));

        TransactionDto dto = resolveCategories(transactionDto);

        Transaction transaction = depositTransactionFromTransactionDto(dto);
        return bankAccount.addTransaction(transaction);
    }

    private Transaction depositTransactionFromTransactionDto(TransactionDto transactionDto) {
        List<TransactionComponent> parts = getComponents(transactionDto);
        return DepositTransaction.of(transactionDto.getPayeeId(), transactionDto.getAmount(), transactionDto.getDate(), transactionDto.getDescription(), parts);
    }

    private Transaction withdrawalTransactionFromTransactionDto(TransactionDto transactionDto) {
        List<TransactionComponent> parts = getComponents(transactionDto);
        return WithdrawalTransaction.of(transactionDto.getPayeeId(), transactionDto.getAmount(), transactionDto.getDate(), transactionDto.getDescription(), parts);
    }

    private static List<TransactionComponent> getComponents(TransactionDto transactionDto) {
        return transactionDto.getComponents()
                .stream()
                .map(tpDTO -> new TransactionComponent(tpDTO.getCategoryId(),
                        tpDTO.getAmount(),
                        tpDTO.getDescription()))
                .toList();
    }

    CategoryId addCategory(String categoryName) {
        if (categoryRepository.findCategoryByName(categoryName).isPresent()) {
            throw new CategoryAlreadyExistsException(categoryName);
        }
        Category category = new Category(CategoryId.getNew(), categoryName);
        Category saved = categoryRepository.save(category);
        return saved.getId();
    }

    List<CategoryDto> findCategories() {
        return categoryRepository.findCategories().stream().map(CategoryDto::new).toList();
    }

    Optional<CategoryDto> findCategory(CategoryId categoryId) {
        return categoryRepository.findCategory(categoryId).map(CategoryDto::new);
    }

    Optional<CategoryDto> findCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name).map(CategoryDto::new);
    }

    private TransactionDto resolveCategories(TransactionDto transactionDto) {
        List<TransactionComponentDto> dtos = new ArrayList<>();
        Set<String> newCategoryNames = new LinkedHashSet<>();
        for (TransactionComponentDto component : transactionDto.getComponents()) {
            if (component.hasNewCategory()) {
                newCategoryNames.add(component.getCategoryName());
            }
        }
        for (String name : newCategoryNames) {
            this.addCategory(name);
        }
        for (TransactionComponentDto component : transactionDto.getComponents()) {
            if (component.hasNewCategory()) {
                Category category = categoryRepository.findCategoryByName(component.getCategoryName()).orElseThrow();
                dtos.add(component.copyWithCategoryId(category.getId()));
            } else
                dtos.add(component);
        }
        return transactionDto.withComponents(dtos);
    }

    void deleteCategory(CategoryId categoryId) {
        if (isAnyTransactionAssociated(categoryId)) {
            Category category = categoryRepository.findCategory(categoryId).orElseThrow();
            throw new CategoryStillAssociatedWithTransactionsException(category);
        }
        categoryRepository.deleteById(categoryId);
    }

    boolean isAnyTransactionAssociated(CategoryId catId) {
        List<BankAccount> accounts = bankAccountRepository.findAll();
        for (BankAccount account : accounts) {
            if (account.isAnyTransactionAssociated(catId))
                return true;
        }
        return false;
    }

    void replaceAndDeleteCategory(CategoryId oldCatId, CategoryId newCatId) {
        bankAccountRepository.findAll().forEach(ba -> ba.replaceCategory(oldCatId, newCatId));
        categoryRepository.deleteById(oldCatId);
    }

    Optional<TransactionDto> getTransaction(BankAccountId bankAccountId, TransactionId id) {
        return bankAccountRepository.findBankAccount(bankAccountId).map(ba -> ba.getTransaction(id)).map(this::fromTransaction);
    }

    TransactionDto fromTransaction(Transaction transaction) {
        return new TransactionDto(transaction.getPayeeId(), transaction.getDate(), transaction.getAmount(), transaction.getDescription(),
                transaction.getComponents().stream().map(t ->
                        new TransactionComponentDto(t.getCategory(),
                                categoryRepository.findCategory(t.getCategory()).orElseThrow().getName(),
                                t.getAmount(), t.getDescription())).toList());
    }
}
