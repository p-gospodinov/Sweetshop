package bg.codeacademy.cakeShop.shedule;


import bg.codeacademy.cakeShop.enums.PaymentCriteria;
import bg.codeacademy.cakeShop.model.ScheduleTransaction;
import bg.codeacademy.cakeShop.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionTaskExecutor implements Runnable {
    private final TransactionService transactionService;
    @Value("${schedule.execution-daily}")
    private int dailyExecutionHour;
    private boolean awake = false;
    private final List<ScheduleTransaction> scheduleTransactionList;

    public TransactionTaskExecutor(TransactionService transactionService) {
        this.transactionService = transactionService;
        scheduleTransactionList = new ArrayList<>();
    }

    public synchronized void start() {
        this.awake = true;
        notify();
    }

    public synchronized void stop() {
        this.awake = false;
    }

    @Override
    public synchronized void run() {
        try {
            while (!awake) {
                wait();
            }
            while (awake) {
                LocalDateTime lt = LocalDateTime.now();
                for (ScheduleTransaction task : scheduleTransactionList) {
                    if (task.getPaymentCriteria().equals(PaymentCriteria.DAILY)) {
                        if (lt.getHour() == dailyExecutionHour) {
                            transactionService.createTransactionBasedOnPercentage(
                                    task.getSender().getBeneficiary().getId(),
                                    task.getSender().getIban(),
                                    task.getRecipient().getIban(),
                                    task.getAmountPercentage());
                        }
                    } else if (task.getPaymentCriteria().equals(PaymentCriteria.MONTHLY)) {
                        LocalDate now = LocalDate.now();
                        LocalDate lastDay = now.with(TemporalAdjusters.lastDayOfMonth());
                        if (now == lastDay) {
                            transactionService.createTransactionBasedOnPercentage(
                                    task.getSender().getBeneficiary().getId(),
                                    task.getSender().getIban(),
                                    task.getRecipient().getIban(),
                                    task.getAmountPercentage());
                        }
                    }
                }
                Thread.sleep(60000);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread was interrupted");
        }
    }

    public void updateTaskList(Iterable<ScheduleTransaction> scheduleTransactions) {
        for (ScheduleTransaction transaction : scheduleTransactions) {
            if (!scheduleTransactionList.contains(transaction)) {
                scheduleTransactionList.add(transaction);
            }
        }
    }

    /*private Transaction formTransaction(ScheduleTransaction task) {
        BankAccount senderBankAccount = task.getSender();
        BankAccount recipientBankAccount = task.getRecipient();
        Transaction transaction = new Transaction();
        transaction.setSenderBankAccount(senderBankAccount);
        transaction.setRecipientBankAccount(recipientBankAccount);
        transaction.setDateTime(LocalDateTime.now());
        transaction.setAmount(12);
        return transaction;
    }*/
}
