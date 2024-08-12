package bg.codeacademy.cakeShop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.util.List;

public record TransferItemDTO(
        @Null
        @NotBlank
        @NotEmpty
        String name,
        int count) {

    public TransferItemDTO(String name,
                           int count
    ) {
        this.name = name;
        this.count = count;
    }
}
