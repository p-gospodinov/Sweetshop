package bg.codeacademy.cakeShop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CommentResponseDTO(
        @NotEmpty
        @NotNull
        @NotBlank
        String text,
        @NotEmpty
        @NotNull
        @NotBlank
        String uin,
        LocalDateTime date
) {
}
