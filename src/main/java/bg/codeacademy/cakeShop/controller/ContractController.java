package bg.codeacademy.cakeShop.controller;

import bg.codeacademy.cakeShop.dto.ContractDTO;
import bg.codeacademy.cakeShop.mapper.Mapper;
import bg.codeacademy.cakeShop.security.AuthenticatedUser;
import bg.codeacademy.cakeShop.service.ContractService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/contracts")
public class ContractController {
    private final ContractService contractService;
    private final Mapper mapper;

    public ContractController(ContractService contractService, Mapper mapper) {
        this.contractService = contractService;
        this.mapper = mapper;
    }

    @PreAuthorize("hasRole('ROLE_SHOP')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createContract(
            Authentication authentication, @Valid @RequestBody ContractDTO dto) throws MessagingException {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
        log.info("Controller | Get all transactions of legal-entity with ID:" + user.getId());
        return new ResponseEntity<>("http://localhost:8080/api/v1/legal-entities/contracts/?id=" +
                contractService.createContract(
                        user.getId(),
                        dto.amount(),
                        dto.currency(),
                        dto.recipientUin()
                ).getId(), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_SHOP', 'ROLE_RENTIER')")
    @PatchMapping
    public ResponseEntity<String> validateContract(
            Authentication authentication, @Valid @RequestParam String identifier) throws MessagingException {
        log.info("Controller | Validate contract with identifier:" + identifier);
           // Authentication authentication, @Valid @RequestParam String identifier) throws MessagingException {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
        return new ResponseEntity<>(contractService.validateContract(user.getId(), identifier).getIdentifier() + " validated successfully.", HttpStatus.OK);
    }
}
