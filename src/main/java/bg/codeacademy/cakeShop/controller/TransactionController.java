package bg.codeacademy.cakeShop.controller;

import bg.codeacademy.cakeShop.dto.TransactionDTO;
import bg.codeacademy.cakeShop.mapper.Mapper;
import bg.codeacademy.cakeShop.security.AuthenticatedUser;
import bg.codeacademy.cakeShop.service.TransactionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final Mapper mapper;

    public TransactionController(TransactionService transactionService, Mapper mapper) {
        this.transactionService = transactionService;

        this.mapper = mapper;
    }

    @PreAuthorize("hasRole('ROLE_SHOP')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createTransaction(
            Authentication authentication, @Valid @RequestBody TransactionDTO dto) {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
        log.info("Controller | Create transaction by legal-entity ID:"+user.getId());
        return new ResponseEntity<>("http://localhost:8080/api/v1/transactions/?id="
              + transactionService.createTransactionBasedOnPercentage(
                      user.getId(),
                dto.senderIban(),
                dto.recipientIban(),
                dto.amountPercentage()
                ).getId(), HttpStatus.CREATED);
    }
}
