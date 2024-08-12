package bg.codeacademy.cakeShop.dto;

import bg.codeacademy.cakeShop.validator.ValidIban;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BuyFromShopDTO(
        @NotNull
        List<TransferItemDTO> items) {
}
