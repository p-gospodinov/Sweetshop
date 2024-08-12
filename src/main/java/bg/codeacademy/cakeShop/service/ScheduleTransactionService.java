package bg.codeacademy.cakeShop.service;

import bg.codeacademy.cakeShop.enums.PaymentCriteria;
import bg.codeacademy.cakeShop.error_handling.exception.BankAccountNotExistException;
import bg.codeacademy.cakeShop.error_handling.exception.InvalidScheduleTransactionException;
import bg.codeacademy.cakeShop.error_handling.exception.ScheduleTransactionExistException;
import bg.codeacademy.cakeShop.error_handling.exception.TransactionException;
import bg.codeacademy.cakeShop.model.BankAccount;
import bg.codeacademy.cakeShop.model.LegalEntity;
import bg.codeacademy.cakeShop.model.ScheduleTransaction;
import bg.codeacademy.cakeShop.model.Transaction;
import bg.codeacademy.cakeShop.repository.ScheduleTransactionRepository;
import bg.codeacademy.cakeShop.shedule.TransactionTaskExecutor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.time.LocalDateTime.now;

@Service
public class ScheduleTransactionService {
    public final ScheduleTransactionRepository scheduleTransactionRepository;
    public final BankAccountService bankAccountService;
    private final ApplicationContext springContext;

    public ScheduleTransactionService(ScheduleTransactionRepository scheduleTransactionRepository,
                                      BankAccountService bankAccountService, ApplicationContext springContext) {
        this.scheduleTransactionRepository = scheduleTransactionRepository;
        this.bankAccountService = bankAccountService;
        this.springContext = springContext;
    }

    public ScheduleTransaction createScheduleTransaction(
            int principalId,
            String senderIban,
            String recipientIban,
            int amountPercentage,
            PaymentCriteria paymentCriteria) {
        if (senderIban.equals(recipientIban)) {
            throw new InvalidScheduleTransactionException("The sender and recipient IBAN can not be same!");
        }

        BankAccount senderAccount = bankAccountService.getBankAccount(principalId, senderIban);
        BankAccount recipientAccount = bankAccountService.getBankAccount(recipientIban);
        ScheduleTransaction transaction = new ScheduleTransaction();
        transaction.setSender(senderAccount);
        transaction.setRecipient(recipientAccount);
        transaction.setPaymentCriteria(paymentCriteria);
        transaction.setAmountPercentage(amountPercentage);

        if (scheduleTransactionRepository
                .existsScheduleTransactionBySenderAndRecipientAndPaymentCriteria(senderAccount
                        , recipientAccount, paymentCriteria)) {
            throw new ScheduleTransactionExistException("Schedule transaction exist!");
        }
        scheduleTransactionRepository.save(transaction);
        TransactionTaskExecutor taskExecutor = springContext.getBean(TransactionTaskExecutor.class);
        taskExecutor.updateTaskList(scheduleTransactionRepository.findAll());
        return transaction;
    }
}
