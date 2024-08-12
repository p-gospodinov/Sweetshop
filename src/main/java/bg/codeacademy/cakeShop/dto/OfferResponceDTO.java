package bg.codeacademy.cakeShop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record OfferResponceDTO(
        @NotNull
        float money,
        @NotBlank
        @NotEmpty
        @NotNull
        String offerorUin,
        @NotBlank
        @NotEmpty
        @NotNull
        String offerorPersonalName,
        @Email
        String offerorEmail,
        @NotBlank
        @NotEmpty
        @NotNull
        String contractIdentifier

) {

    public OfferResponceDTO(float money,
                            String offerorUin,
                            String offerorPersonalName,
                            String offerorEmail,
                            String contractIdentifier
    ) {
        this.money = money;
        this.offerorUin = offerorUin;
        this.offerorPersonalName = offerorPersonalName;
        this.offerorEmail = offerorEmail;
        this.contractIdentifier = contractIdentifier;
    }
}
