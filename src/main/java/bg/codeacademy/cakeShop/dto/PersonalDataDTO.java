package bg.codeacademy.cakeShop.dto;

import bg.codeacademy.cakeShop.enums.Role;
import bg.codeacademy.cakeShop.validator.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PersonalDataDTO(
        @NotEmpty
        @NotNull
        @NotBlank
        String userName,
        @NotEmpty
        @NotNull
        @NotBlank
        String password,

        @ValidEnum(enumClass = Role.class)
        String role,
        @NotEmpty
        @NotNull
        @NotBlank
        String personalName,
        AddressDTO address,
        List<BankAccountDTO> bankAccount

) {

    public PersonalDataDTO(String userName,
                           String password,
                           String role,
                           String personalName,
                           AddressDTO address,
                           List<BankAccountDTO> bankAccount
    ) {
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.personalName = personalName;
        this.address = address;
        this.bankAccount = bankAccount;
    }
}
