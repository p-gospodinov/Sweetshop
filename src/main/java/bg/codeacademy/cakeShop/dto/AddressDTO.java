package bg.codeacademy.cakeShop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddressDTO(
        @NotNull
        @NotEmpty
        @NotBlank
        @Size(max = 20)
        String city,
        @NotNull
        @NotEmpty
        @NotBlank
        @Size(max = 20)
        String street
) {

    public AddressDTO(String city,
                      String street

    ) {
        this.city = city;
        this.street = street;
    }
}
