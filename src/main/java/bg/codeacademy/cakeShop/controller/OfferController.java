package bg.codeacademy.cakeShop.controller;

import bg.codeacademy.cakeShop.dto.OfferDTO;
import bg.codeacademy.cakeShop.enums.Currency;
import bg.codeacademy.cakeShop.mapper.Mapper;
import bg.codeacademy.cakeShop.security.AuthenticatedUser;
import bg.codeacademy.cakeShop.service.OfferService;
import jakarta.mail.MessagingException;
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
@RequestMapping("/api/v1/offers")
public class OfferController {
    private final OfferService offerService;
    private final Mapper mapper;

    public OfferController(OfferService offerService, Mapper mapper) {
        this.offerService = offerService;

        this.mapper = mapper;
    }

    @PreAuthorize("hasRole('ROLE_DELIVER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createOffer(
            Authentication authentication, @Valid @RequestBody OfferDTO dto) throws MessagingException {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
        log.info("Controller | Create offer by legal-entity ID:"+user.getId());
        return new ResponseEntity<>("http://localhost:8080/api/v1/legal-entities/offers/?id="
                + user.getId() + offerService
                .createOffer(user.getId(), dto.offeredId(), dto.money(), Currency.valueOf(dto.currency())).getId(), HttpStatus.CREATED);
    }
}
