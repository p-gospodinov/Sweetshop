package bg.codeacademy.cakeShop.service;

import bg.codeacademy.cakeShop.enums.Currency;
import bg.codeacademy.cakeShop.model.*;
import bg.codeacademy.cakeShop.repository.TurnoverRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;
import static java.time.LocalDate.now;
import static org.mockito.Mockito.*;

class TurnoverServiceTest {
    public static final TurnoverRepository turnoverRepository = mock(TurnoverRepository.class);
    public static LegalEntityService legalEntityService = mock(LegalEntityService.class);
    public static TurnoverService turnoverService;

    @BeforeAll
    public static void setup() {
        turnoverService = new TurnoverService(turnoverRepository, legalEntityService);
    }

    @Test
    void shouldUpdateExistingTurnover() {
        LegalEntity owner = formLegalEntity();
        Turnover turnover = formTurnover(100, owner);
        when(turnoverRepository.findTurnoverByDate(now())).thenReturn(turnover);
        Turnover response = turnoverService.additionAmount(1, 200);
        Assertions.assertEquals(300,response.getAmount());
        verify(turnoverRepository,times(1)).save(turnover);
    }
    @Test
    void shouldCreateTurnover() {
        LegalEntity owner = formLegalEntity();
        Turnover turnover = formTurnover(100, owner);
        when(turnoverRepository.findTurnoverByDate(now())).thenReturn(null);
        when(legalEntityService.getLegalEntity(1)).thenReturn(owner);
        Turnover response = turnoverService.additionAmount(1, 200);
        Assertions.assertEquals(200,response.getAmount());
        verify(turnoverRepository,times(1)).save(response);
    }

    private Turnover formTurnover(float amount, LegalEntity owner) {
        Turnover turnover = new Turnover();
        turnover.setDate(now());
        turnover.setAmount(amount);
        turnover.setOwner(owner);
        return turnover;
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