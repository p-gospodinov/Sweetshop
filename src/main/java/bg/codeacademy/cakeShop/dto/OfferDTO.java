package bg.codeacademy.cakeShop.dto;

import bg.codeacademy.cakeShop.enums.Currency;
import bg.codeacademy.cakeShop.validator.ValidEnum;
import jakarta.validation.constraints.NotNull;

public record OfferDTO(
        @NotNull
        float money,
        @ValidEnum(enumClass = Currency.class)
        String currency,
        @NotNull
        int offeredId
) {
    public OfferDTO(float money,
                    String currency,
                    int offeredId
    ) {
        this.money = money;
        this.currency = currency;
        this.offeredId = offeredId;
    }
}
