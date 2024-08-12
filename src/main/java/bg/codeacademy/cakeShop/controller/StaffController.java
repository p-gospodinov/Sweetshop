package bg.codeacademy.cakeShop.controller;

import bg.codeacademy.cakeShop.dto.PersonalDataDTO;
import bg.codeacademy.cakeShop.mapper.Mapper;
import bg.codeacademy.cakeShop.model.PersonalData;
import bg.codeacademy.cakeShop.security.AuthenticatedUser;
import bg.codeacademy.cakeShop.service.StaffService;
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
@RequestMapping("/api/v1/staff")
public class StaffController {
    private final StaffService staffService;
    private final Mapper mapper;

    public StaffController(StaffService staffService, Mapper mapper) {
        this.staffService = staffService;
        this.mapper = mapper;
    }

    @PreAuthorize("hasRole('ROLE_SHOP')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createStaff(
            Authentication authentication, @Valid @RequestBody PersonalDataDTO dto) {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
        log.info("Controller | Create staff by legal-entity with ID:"+user.getId());
        PersonalData personalData = mapper.mapToPersonalData(dto);
        return new ResponseEntity<>("http://localhost:8080/api/v1/legal-entities/shop/?id="
                + user.getId() + "/staff/?id=" + staffService.createStaff
                (personalData, user.getId()).getId(), HttpStatus.CREATED);
    }
}
