package bg.codeacademy.cakeShop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ItemDTO(
        @NotNull
        @NotEmpty
        @NotBlank
        String name,
        float price,
        int count) {
}
