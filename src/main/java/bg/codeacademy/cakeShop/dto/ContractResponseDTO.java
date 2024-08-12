package bg.codeacademy.cakeShop.dto;

import bg.codeacademy.cakeShop.enums.Currency;
import bg.codeacademy.cakeShop.validator.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ContractResponseDTO(
        @NotEmpty
        @NotNull
        @NotBlank
        String identifier,
        float amount,
        @ValidEnum(enumClass = Currency.class)
        String currency,
        @NotEmpty
        @NotNull
        @NotBlank
        String senderUin,
        @NotEmpty
        @NotNull
        @NotBlank
        String recipientUin,
        @NotEmpty
        @NotNull
        @NotBlank
        String status
) {

}
