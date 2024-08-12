package bg.codeacademy.cakeShop.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DeliveryRequestDTO(
        int ownerId,
        @NotNull
        List<ItemDTO> items) {

    public DeliveryRequestDTO(int ownerId,
                              List<ItemDTO> items
    ) {
        this.ownerId = ownerId;
        this.items = items;
    }
}
