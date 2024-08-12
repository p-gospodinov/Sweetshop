package bg.codeacademy.cakeShop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LegalEntityResponse(
        int id,
        @Email
        String email,
        @NotEmpty
        @NotNull
        @NotBlank
        String uin,
        PersonalDataDTO personalData
) {
    public LegalEntityResponse(int id,
                               String email,
                               String uin,
                               PersonalDataDTO personalData

    ) {
        this.id = id;
        this.email = email;
        this.uin = uin;
        this.personalData = personalData;
    }
}