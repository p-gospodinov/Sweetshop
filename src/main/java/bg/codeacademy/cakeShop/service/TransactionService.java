package bg.codeacademy.cakeShop.service;

import bg.codeacademy.cakeShop.error_handling.exception.TransactionException;
import bg.codeacademy.cakeShop.model.BankAccount;
import bg.codeacademy.cakeShop.model.Transaction;
import bg.codeacademy.cakeShop.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.now;

@Service
public class TransactionService {
    public final TransactionRepository transactionRepository;
    public final BankAccountService bankAccountService;

    public TransactionService(TransactionRepository transactionRepository, BankAccountService bankAccountService) {
        this.transactionRepository = transactionRepository;
        this.bankAccountService = bankAccountService;
    }

    @Transactional
    public Transaction createTransactionBasedOnPercentage(int principalId, String senderIban,
                                                          String recipientIban, int amountPercentage) {
        BankAccount senderAccount = bankAccountService.getBankAccount(principalId, senderIban);
        BankAccount recipientAccount = bankAccountService.getBankAccount(recipientIban);

        if (senderAccount.getAmount() <= 0) {
            throw new TransactionException("Sender have no money in bank account!");
        }
        float amount = calculatePercentage(senderAccount.getAmount(), amountPercentage);

        float newSenderAmount = senderAccount.getAmount() - amount;
        senderAccount.setAmount(newSenderAmount);
        bankAccountService.update(senderAccount);

        float newRecipientAmount = recipientAccount.getAmount() + amount;
        recipientAccount.setAmount(newRecipientAmount);
        bankAccountService.update(recipientAccount);
        Transaction transaction = new Transaction();
        transaction.setSenderBankAccount(senderAccount);
        transaction.setRecipientBankAccount(recipientAccount);
        transaction.setDateTime(now());
        transaction.setAmount(amount);
        transactionRepository.save(transaction);
        return transaction;
    }

    @Transactional
    public Transaction createTransactionBasedOnAmount(int principalId, String senderIban,
                                                      String recipientIban, float amount) {
        BankAccount senderAccount = bankAccountService.getBankAccount(principalId, senderIban);
        BankAccount recipientAccount = bankAccountService.getBankAccount(recipientIban);

        if (senderAccount.getAmount() <= 0 || senderAccount.getAmount() < amount) {
            throw new TransactionException("Sender have no enough money in bank account!");
        }
        float newSenderAmount = senderAccount.getAmount() - amount;
        senderAccount.setAmount(newSenderAmount);
        bankAccountService.update(senderAccount);

        float newRecipientAmount = recipientAccount.getAmount() + amount;
        recipientAccount.setAmount(newRecipientAmount);
        bankAccountService.update(recipientAccount);
        Transaction transaction = new Transaction();
        transaction.setSenderBankAccount(senderAccount);
        transaction.setRecipientBankAccount(recipientAccount);
        transaction.setDateTime(now());
        transaction.setAmount(amount);
        transactionRepository.save(transaction);
        return transaction;
    }

    private float calculatePercentage(float inValue, int percent) {
        float percentFloat = (float) percent / 100;
        return (inValue * percentFloat);
    }
}
