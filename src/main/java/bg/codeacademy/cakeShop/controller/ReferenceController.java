package bg.codeacademy.cakeShop.controller;

import bg.codeacademy.cakeShop.dto.BankAccountDTO;
import bg.codeacademy.cakeShop.mapper.Mapper;
import bg.codeacademy.cakeShop.model.BankAccount;
import bg.codeacademy.cakeShop.security.AuthenticatedUser;
import bg.codeacademy.cakeShop.service.BankAccountService;
import bg.codeacademy.cakeShop.service.ReferenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/v1/references")
public class ReferenceController {
    private final ReferenceService referenceService;
    private final Mapper mapper;

    public ReferenceController(ReferenceService referenceService, Mapper mapper) {
        this.referenceService = referenceService;
        this.mapper = mapper;
    }

    @PreAuthorize("hasAnyRole('ROLE_WORKER', 'ROLE_MANAGER')")
    @GetMapping("/earnings")
    public ResponseEntity<List<BankAccountDTO>> getEarning(Authentication authentication) {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
        log.info("Controller | Get money reference by legal-entity ID:"+user.getId());
        List<BankAccount> bankAccountList = referenceService.getEarningReference(user.getId());
        List<BankAccountDTO> dtoList = mapper.mapToBankAccountDtoList(bankAccountList);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
}
