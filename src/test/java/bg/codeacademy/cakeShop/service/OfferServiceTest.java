package bg.codeacademy.cakeShop.service;

import bg.codeacademy.cakeShop.enums.Currency;
import bg.codeacademy.cakeShop.enums.Status;
import bg.codeacademy.cakeShop.error_handling.exception.InvalidOfferException;
import bg.codeacademy.cakeShop.error_handling.exception.OfferExistException;
import bg.codeacademy.cakeShop.model.*;
import bg.codeacademy.cakeShop.repository.OfferRepository;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OfferServiceTest {
    static OfferService offerService;
    static OfferRepository offerRepository = mock(OfferRepository.class);
    static LegalEntityService legalEntityService = mock(LegalEntityService.class);
    static ContractService contractService = mock(ContractService.class);

    @BeforeAll
    public static void setup() {
        offerService = new OfferService(offerRepository, legalEntityService, contractService);
    }

    @Test
    void shouldCreateOffer() throws MessagingException {
        LegalEntity offeror = formLegalEntity("A");
        LegalEntity offered = formLegalEntity("B");
        Contract contract = formContract(offeror, offered);
        Offer offer = formOffer("A", "B");
        when(legalEntityService.getLegalEntity(0))
                .thenReturn(offeror);
        when(legalEntityService.getLegalEntity(2))
                .thenReturn(offered);
        when(offerRepository.existsOfferByOfferorAndMoney(offer.getOfferor(), offer.getMoney()))
                .thenReturn(false);
        when(contractService.createContract(
                contract.getOfferor().getId(),
                contract.getAmount(),
                String.valueOf(contract.getCurrency()),
                contract.getRecipient().getUin())).thenReturn(contract);
        Offer response = offerService.createOffer(0, 2, 100, Currency.BG);
        verify(contractService, times(1)).createContract(
                contract.getOfferor().getId(),
                contract.getAmount(),
                String.valueOf(contract.getCurrency()),
                contract.getRecipient().getUin());
        verify(offerRepository, times(1)).save(response);
    }

    @Test
    void shouldThrowInvalidOfferException() {
        LegalEntity offeror = formLegalEntity("A");
        LegalEntity offered = formLegalEntity("A");
        Offer offer = formOffer("A", "B");
        when(legalEntityService.getLegalEntity(1))
                .thenReturn(offeror);
        when(legalEntityService.getLegalEntity(1))
                .thenReturn(offered);
        Assertions.assertThrows(InvalidOfferException.class, () -> {
            offerService.createOffer(1, 1, 200, Currency.BG);
        });
    }

    @Test
    void shouldThrowOfferExistException() {
        LegalEntity offeror = formLegalEntity("A");
        LegalEntity offered = formLegalEntity("B");
        when(legalEntityService.getLegalEntity(1))
                .thenReturn(offeror);
        when(legalEntityService.getLegalEntity(2))
                .thenReturn(offered);
        when(offerRepository.existsOfferByOfferorAndMoney(offeror, 200))
                .thenReturn(true);
        Assertions.assertThrows(OfferExistException.class, () -> {
            offerService.createOffer(1, 2, 200, Currency.BG);
        });
    }

    private Offer formOffer(String oferrorUIN, String offeredUIN) {
        LegalEntity offeror = formLegalEntity(oferrorUIN);
        LegalEntity offered = formLegalEntity(offeredUIN);
        Offer offer = new Offer();
        offer.setOfferor(offeror);
        offer.setMoney(120);
        offer.setOffered(offered);
        return offer;
    }

    private LegalEntity formLegalEntity(String uin) {
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
        legalEntity.setUin(uin);
        legalEntity.setPersonalData(personalData);
        return legalEntity;
    }

    private Contract formContract(LegalEntity offeror, LegalEntity offered) {
        Contract contract = new Contract();
        contract.setAmount(100);
        contract.setCurrency(Currency.BG);
        contract.setOfferor(offeror);
        contract.setRecipient(offered);
        contract.setStatus(Status.PENDING);
        return contract;
    }
}