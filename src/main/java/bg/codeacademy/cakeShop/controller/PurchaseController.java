package bg.codeacademy.cakeShop.controller;

import bg.codeacademy.cakeShop.dto.BuyFromShopDTO;
import bg.codeacademy.cakeShop.dto.CommentDTO;
import bg.codeacademy.cakeShop.dto.TransferItemDTO;
import bg.codeacademy.cakeShop.mapper.Mapper;
import bg.codeacademy.cakeShop.service.CommentService;
import bg.codeacademy.cakeShop.service.PurchaseService;
import bg.codeacademy.cakeShop.service.StorageService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final Mapper mapper;

    public PurchaseController(PurchaseService purchaseService, Mapper mapper) {
        this.purchaseService = purchaseService;
        this.mapper = mapper;
    }


    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Integer>> purchaseItems(
            @RequestParam int shopId,
            @Valid @RequestBody BuyFromShopDTO dto) {
        log.info("Controller | Purchase items.");
        Map<String, Integer> purchaseList = mapper.mapTransferItemDto(dto.items());
        Map<String, Integer> purchasesRespond = purchaseService.purchaseItems(shopId, purchaseList);
        return new ResponseEntity<>(purchasesRespond, HttpStatus.OK);
    }
}
