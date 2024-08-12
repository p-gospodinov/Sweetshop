package bg.codeacademy.cakeShop.service;

import bg.codeacademy.cakeShop.enums.Currency;
import bg.codeacademy.cakeShop.error_handling.exception.TransactionException;
import bg.codeacademy.cakeShop.model.BankAccount;
import bg.codeacademy.cakeShop.model.Transaction;
import bg.codeacademy.cakeShop.repository.TransactionRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class TransactionServiceTest {
    static TransactionService transactionService;
    public static final TransactionRepository transactionRepository = mock(TransactionRepository.class);
    public static final BankAccountService bankAccountService = mock(BankAccountService.class);

    @BeforeAll
    public static void setup() {
        transactionService = new TransactionService(
                transactionRepository,
                bankAccountService);
    }

    @Test
    void shouldCreateTransaction() {
        int principal = 1;
        int percentage = 50;
        BankAccount senderAccount = formBankAccount(1000, "iban1");
        BankAccount recipientAccount = formBankAccount(0, "iban2");
        when(bankAccountService.getBankAccount(principal, senderAccount.getIban())).thenReturn(senderAccount);
        when(bankAccountService.getBankAccount(recipientAccount.getIban())).thenReturn(recipientAccount);
        float amount = calculatePercentage(senderAccount.getAmount(), percentage);
        float newSenderAmount = senderAccount.getAmount() - amount;
        senderAccount.setAmount(newSenderAmount);
        Transaction response = transactionService.createTransactionBasedOnPercentage(
                principal, senderAccount.getIban(), recipientAccount.getIban(), percentage);
        verify(bankAccountService, times(1)).update(senderAccount);
        float newRecipientAmount = recipientAccount.getAmount() + amount;
        recipientAccount.setAmount(newRecipientAmount);
        verify(bankAccountService, times(1)).update(recipientAccount);
        verify(transactionRepository, times(1)).save(response);
    }

    @Test
    void shouldThrowTransactionException() {
        int principal = 1;
        int percentage = 50;
        BankAccount senderAccount = formBankAccount(0, "iban1");
        BankAccount recipientAccount = formBankAccount(0, "iban2");
        when(bankAccountService.getBankAccount(principal, senderAccount.getIban())).thenReturn(senderAccount);
        when(bankAccountService.getBankAccount(recipientAccount.getIban())).thenReturn(recipientAccount);
        Assert.assertThrows(TransactionException.class, () -> {
            transactionService.createTransactionBasedOnPercentage(
                    principal, senderAccount.getIban(), recipientAccount.getIban(), percentage);
        });
    }

    private BankAccount formBankAccount(int amount, String iban1) {
        BankAccount account = new BankAccount();
        account.setId(1);
        account.setAmount(amount);
        account.setIban(iban1);
        account.setCurrency(Currency.BG);
        return account;
    }

    private float calculatePercentage(float inValue, int percent) {
        float percentFloat = (float) percent / 100;
        return (inValue * percentFloat);
    }
}