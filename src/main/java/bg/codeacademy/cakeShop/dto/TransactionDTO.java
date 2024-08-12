package bg.codeacademy.cakeShop.dto;

import jakarta.validation.constraints.NotNull;

public record TransactionDTO(
        @NotNull
        String senderIban,
        @NotNull
        String recipientIban,
        @NotNull
        int amountPercentage
) {

    public TransactionDTO(String senderIban,
                          String recipientIban,
                          int amountPercentage
    ) {
        this.senderIban = senderIban;
        this.recipientIban = recipientIban;
        this.amountPercentage = amountPercentage;
    }
}
