package bg.codeacademy.cakeShop.dto;

import bg.codeacademy.cakeShop.enums.BankAccountType;
import bg.codeacademy.cakeShop.enums.Currency;
import bg.codeacademy.cakeShop.validator.ValidEnum;
import bg.codeacademy.cakeShop.validator.ValidIban;
import jakarta.validation.constraints.NotNull;

public record BankAccountDTO(

        @ValidIban
        String iban,
        @NotNull
        float amount,
        @ValidEnum(enumClass = Currency.class)
        String currency,
        @NotNull
        @ValidEnum(enumClass = BankAccountType.class)
        BankAccountType bankAccountType
) {

    public BankAccountDTO(String iban,
                          float amount,
                          String currency,
                          BankAccountType bankAccountType
    ) {
        this.iban = iban;
        this.amount = amount;
        this.currency = currency;
        this.bankAccountType = bankAccountType;
    }
}
