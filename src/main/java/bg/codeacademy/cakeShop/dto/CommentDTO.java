package bg.codeacademy.cakeShop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CommentDTO(
        @NotEmpty
        @NotNull
        @NotBlank
        String text,
        @NotEmpty
        @NotNull
        @NotBlank
        String uin
) {
}
