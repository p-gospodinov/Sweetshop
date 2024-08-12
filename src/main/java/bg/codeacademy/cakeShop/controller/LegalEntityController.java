package bg.codeacademy.cakeShop.controller;

import bg.codeacademy.cakeShop.dto.*;
import bg.codeacademy.cakeShop.mapper.Mapper;
import bg.codeacademy.cakeShop.model.*;
import bg.codeacademy.cakeShop.security.AuthenticatedUser;
import bg.codeacademy.cakeShop.service.LegalEntityService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@EnableMethodSecurity
@Slf4j
@RestController
@RequestMapping("/api/v1/legal-entities")
public class LegalEntityController {
    private final LegalEntityService legalEntityService;
    private final Mapper mapper;

    public LegalEntityController(LegalEntityService legalEntityService, Mapper mapper) {
        this.legalEntityService = legalEntityService;
        this.mapper = mapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@Valid @RequestBody LegalEntityRegistrationDTO dto) {
        LegalEntity legalEntity = mapper.mapToLegalEntity(dto);
        log.info("Controller | Create legal-entity with UIN:" + dto.uin());
        return new ResponseEntity<>("http://localhost:8080/api/v1/legal-entities/?id="
                + legalEntityService.createLegalEntity(legalEntity).getId(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LegalEntityResponse>> getAll() {
        log.info("Controller | Get all legal-entity");
        List<LegalEntityResponse> responseList = mapper.mapLegalEntityToResponseList(
                legalEntityService.getLegalEntities()
        );
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<LegalEntityResponse> get(@RequestParam("id") int id) {
        log.info("Controller | Get legal-entity with ID=" + id);
        LegalEntityResponse response = mapper.mapLegalEntityToResponse(
                legalEntityService.getLegalEntity(id)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/offers")
    public ResponseEntity<Map<String, List<OfferResponceDTO>>> getOffers(Authentication authentication) {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
        log.info("Controller | Get all offers for legal-entity with ID=" + user.getId());
        Map<String, List<Offer>> offers = legalEntityService.getOffers(user.getId());
        Map<String, List<OfferResponceDTO>> dtoList = mapper.mapToOfferResponceDTOList(offers);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SHOP')")
    @GetMapping("/bank-accounts/schedule-transactions")
    public ResponseEntity<List<ScheduleTransactionDTO>> getScheduleTransactions(
            Authentication authentication) {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
        log.info("Controller | Get all schedule-transactions for legal-entity with ID=" + user.getId());
        List<ScheduleTransaction> transactionList = legalEntityService.getScheduleTransactions(user.getId());
        List<ScheduleTransactionDTO> transactionDtoList = mapper.mapToScheduleTransactionDTOList(transactionList);
        return new ResponseEntity<>(transactionDtoList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_SHOP', 'ROLE_RENTIER', 'ROLE_DELIVER')")
    @GetMapping("/contracts")
    public ResponseEntity<Map<String, List<ContractResponseDTO>>> getContracts(
            Authentication authentication) {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
        log.info("Controller | Get all contracts for legal-entity with ID=" + user.getId());
        Map<String, List<Contract>> transactionList = legalEntityService.getLegalEntityContracts(user.getId());
        Map<String, List<ContractResponseDTO>> transactionDtoList = mapper.mapToContractListDto(transactionList);
        return new ResponseEntity<>(transactionDtoList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_SHOP', 'ROLE_RENTIER', 'ROLE_DELIVER')")
    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponseDTO>> getComments(
            Authentication authentication) {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
        log.info("Controller | Get all comments for legal-entity with ID=" + user.getId());
        List<Comment> commentList = legalEntityService.getComments(user.getId());
        List<CommentResponseDTO> commentDtoList = mapper.mapToCommentListDto(commentList);
        return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_SHOP', 'ROLE_DELIVER')")
    @GetMapping("/storages")
    public ResponseEntity<Map<String, Integer>> getStock(
            Authentication authentication) {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
        log.info("Controller | Get stock for legal-entity with ID=" + user.getId());
        List<Storage> storage = legalEntityService.getStock(user.getId());
        Map<String, Integer> items = mapper.mapToItemList(storage);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_SHOP', 'ROLE_DELIVER')")
    @GetMapping("/turnovers")
    public ResponseEntity<Map<LocalDate, Float>> getTurnover(
            Authentication authentication) {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
        log.info("Controller | Get turnover for legal-entity with ID=" + user.getId());
        Map<LocalDate, Float> turnovers = legalEntityService.getTurnovers(user.getId());
        return new ResponseEntity<>(turnovers, HttpStatus.OK);
    }
}
