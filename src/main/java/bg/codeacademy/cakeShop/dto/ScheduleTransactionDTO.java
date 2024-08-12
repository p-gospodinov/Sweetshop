package bg.codeacademy.cakeShop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ScheduleTransactionDTO(
        @NotNull
        @NotEmpty
        @NotBlank
        String senderBankAccountIban,
        @NotNull
        @NotEmpty
        @NotBlank
        String recipientBankAccountIban,
        int amountPercentage,
        String paymentCriteria
) {

    public ScheduleTransactionDTO(String senderBankAccountIban,
                                  String recipientBankAccountIban,
                                  int amountPercentage,
                                  String paymentCriteria

    ) {
        this.senderBankAccountIban = senderBankAccountIban;
        this.recipientBankAccountIban = recipientBankAccountIban;
        this.amountPercentage = amountPercentage;
        this.paymentCriteria = paymentCriteria;
    }
}
