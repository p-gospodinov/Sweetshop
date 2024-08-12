package bg.codeacademy.cakeShop.service;

import bg.codeacademy.cakeShop.enums.Currency;
import bg.codeacademy.cakeShop.model.*;
import bg.codeacademy.cakeShop.repository.CommentRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    public static CommentService commentService;
    public static CommentRepository commentRepository = mock(CommentRepository.class);
    public static LegalEntityService legalEntityService = mock(LegalEntityService.class);

    @BeforeAll
    public static void setup() {
        commentService = new CommentService(commentRepository, legalEntityService);
    }

    @Test
    void shouldCreateComment() {
        LegalEntity assessed = formLegalEntity();
        when(legalEntityService.getLegalEntity(assessed.getUin())).thenReturn(assessed);
        Comment response = commentService.createComment(assessed.getUin(), "My text");
        verify(commentRepository, times(1)).save(response);
    }

    private LegalEntity formLegalEntity() {
        Address address = new Address();
        address.setCity("city");
        address.setStreet("street");
        BankAccount account = new BankAccount();
        account.setIban("BG18RZBB91550123456789");
        account.setCurrency(Currency.BG);
        PersonalData personalData = new PersonalData();
        personalData.setUserName("test");
        personalData.setAddress(address);
        personalData.setBankAccount(List.of(account));
        personalData.setUserPassword("password");
        personalData.setPersonalName("personalName");
        LegalEntity legalEntity = new LegalEntity();
        legalEntity.setEmail("someEmail");
        legalEntity.setUin("UIN");
        legalEntity.setPersonalData(personalData);
        return legalEntity;
    }
}