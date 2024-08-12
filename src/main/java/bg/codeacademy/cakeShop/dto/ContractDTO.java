package bg.codeacademy.cakeShop.dto;

import bg.codeacademy.cakeShop.enums.Currency;
import bg.codeacademy.cakeShop.validator.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ContractDTO(
        float amount,
        @ValidEnum(enumClass = Currency.class)
        String currency,
        @NotEmpty
        @NotNull
        @NotBlank
        String recipientUin
) {

}
