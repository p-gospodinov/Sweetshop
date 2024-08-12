package bg.codeacademy.cakeShop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LegalEntityRegistrationDTO(
        @Email
        String email,
        @NotEmpty
        @NotNull
        @NotBlank
        String uin,
        PersonalDataDTO personalData
) {
    public LegalEntityRegistrationDTO(String email,
                                      String uin,
                                      PersonalDataDTO personalData

    ) {
        this.email = email;
        this.uin = uin;
        this.personalData = personalData;
    }
}