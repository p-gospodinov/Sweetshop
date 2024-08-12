package bg.codeacademy.cakeShop.controller;


import bg.codeacademy.cakeShop.dto.DeliveryRequestDTO;
import bg.codeacademy.cakeShop.dto.ChargeShopStorageDTO;
import bg.codeacademy.cakeShop.mapper.Mapper;
import bg.codeacademy.cakeShop.model.Item;
import bg.codeacademy.cakeShop.security.AuthenticatedUser;
import bg.codeacademy.cakeShop.service.StorageService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/v1/storages")

public class StorageController {

    @Autowired
    private StorageService storageService;
    private final Mapper mapper;

    public StorageController(Mapper mapper) {
        this.mapper = mapper;
    }

    @PreAuthorize("hasRole('ROLE_DELIVER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Item>> createItemInDeliveryStorage(
            Authentication authentication, @RequestBody @Valid DeliveryRequestDTO dto) {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
        Map<Item, Integer> items = mapper.mapDeliveryRequestDTOToItemsList(dto.items());
        Map<Item, Integer> itemsResponse = storageService.createItemInStorage(user.getId(), items);
        log.info("Controller | Create items in delivery storage");
        Set<Item> itemsNames = itemsResponse.keySet();
        return new ResponseEntity<>(itemsNames, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_SHOP')")
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Integer>> putItemInShopStorage(
            Authentication authentication, @RequestBody @Valid ChargeShopStorageDTO dto) {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
        Map<String, Integer> items = mapper.mapTransferItemDto(dto.items());
        Map<String, Integer> itemsResponse = storageService.putItemInShopStorage(
                user.getId(),  dto.deliveryId(),  items);
        log.info("Controller | Put items in shop storage");
        return new ResponseEntity<>(itemsResponse, HttpStatus.OK);
    }
}
