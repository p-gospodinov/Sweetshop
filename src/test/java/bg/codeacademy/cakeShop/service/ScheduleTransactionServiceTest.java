package bg.codeacademy.cakeShop.service;

import bg.codeacademy.cakeShop.enums.Currency;
import bg.codeacademy.cakeShop.enums.PaymentCriteria;
import bg.codeacademy.cakeShop.error_handling.exception.InvalidScheduleTransactionException;
import bg.codeacademy.cakeShop.error_handling.exception.ScheduleTransactionExistException;
import bg.codeacademy.cakeShop.model.*;
import bg.codeacademy.cakeShop.repository.ScheduleTransactionRepository;
import bg.codeacademy.cakeShop.shedule.TransactionTaskExecutor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static org.mockito.Mockito.*;

class ScheduleTransactionServiceTest {
    public static ScheduleTransactionService scheduleTransactionService;
    private static final ScheduleTransactionRepository scheduleTransactionRepository
            = mock(ScheduleTransactionRepository.class);
    private static final ApplicationContext context
            = mock(ApplicationContext.class);
    private static final BankAccountService bankAccountService
            = mock(BankAccountService.class);
    private static final TransactionTaskExecutor taskExecutor
            = mock(TransactionTaskExecutor.class);


    @BeforeAll
    public static void setup() {
        scheduleTransactionService = new ScheduleTransactionService(
                scheduleTransactionRepository, bankAccountService, context
        );
    }

    @Test
    void shouldCreateScheduleTransaction() {
        when(context.getBean(TransactionTaskExecutor.class)).thenReturn(taskExecutor);
        int principalId = 1;
        PaymentCriteria paymentCriteria = PaymentCriteria.DAILY;
        BankAccount senderAccount = formBankAccount(1000, "iban1");
        BankAccount recipientAccount = formBankAccount(0, "iban2");
        when(bankAccountService.getBankAccount(principalId, senderAccount.getIban())).thenReturn(senderAccount);
        when(bankAccountService.getBankAccount(recipientAccount.getIban())).thenReturn(recipientAccount);
        when(scheduleTransactionRepository
                .existsScheduleTransactionBySenderAndRecipientAndPaymentCriteria(
                        senderAccount,
                        recipientAccount,
                        paymentCriteria)).thenReturn(false);
        ScheduleTransaction response
                = scheduleTransactionService.createScheduleTransaction(
                principalId,
                senderAccount.getIban(),
                recipientAccount.getIban(),
                50,
                paymentCriteria);
        verify(scheduleTransactionRepository, times(1)).save(response);
    }

    @Test
    void shouldThrowInvalidScheduleTransactionException() {
        Assertions.assertThrows(InvalidScheduleTransactionException.class, () -> {
            scheduleTransactionService.createScheduleTransaction(
                    1,
                    "A",
                    "A",
                    50,
                    PaymentCriteria.DAILY);
        });
    }

    @Test
    void shouldThrowScheduleTransactionExistException() {
        int principalId = 1;
        PaymentCriteria paymentCriteria = PaymentCriteria.DAILY;
        BankAccount senderAccount = formBankAccount(1000, "iban1");
        BankAccount recipientAccount = formBankAccount(0, "iban2");
        when(bankAccountService.getBankAccount(principalId, senderAccount.getIban())).thenReturn(senderAccount);
        when(bankAccountService.getBankAccount(recipientAccount.getIban())).thenReturn(recipientAccount);
        when(scheduleTransactionRepository
                .existsScheduleTransactionBySenderAndRecipientAndPaymentCriteria(
                        senderAccount,
                        recipientAccount,
                        paymentCriteria)).thenReturn(true);
        Assertions.assertThrows(ScheduleTransactionExistException.class, () -> {
            scheduleTransactionService.createScheduleTransaction(
                    principalId,
                    senderAccount.getIban(),
                    recipientAccount.getIban(),
                    50,
                    paymentCriteria);
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
}
