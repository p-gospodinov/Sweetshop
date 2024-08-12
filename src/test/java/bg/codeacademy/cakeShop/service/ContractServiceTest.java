package bg.codeacademy.cakeShop.service;

import bg.codeacademy.cakeShop.enums.Currency;
import bg.codeacademy.cakeShop.enums.Status;
import bg.codeacademy.cakeShop.error_handling.exception.ContractAlreadyValidatedException;
import bg.codeacademy.cakeShop.error_handling.exception.ContractNotFoundException;
import bg.codeacademy.cakeShop.error_handling.exception.InvalidContractException;
import bg.codeacademy.cakeShop.error_handling.exception.UniqueIdentificationNumberExistException;
import bg.codeacademy.cakeShop.model.*;
import bg.codeacademy.cakeShop.repository.ContractRepository;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class ContractServiceTest {
    public static final ContractRepository contractRepository = mock(ContractRepository.class);
    public static final LegalEntityService legalEntityService = mock(LegalEntityService.class);
    public static ContractService contractService;

    @BeforeAll
    public static void setup() {
        contractService = new ContractService(contractRepository, legalEntityService);
    }

    @Test
    void shouldCreateContract() throws MessagingException {
        LegalEntity offeror = formLegalEntity("A");
        LegalEntity recipient = formLegalEntity("B");
        Contract contract1 = formContract("", offeror, recipient);
        when(legalEntityService.getLegalEntity(1)).thenReturn(offeror);
        when(legalEntityService.getLegalEntity("B")).thenReturn(recipient);
        when(contractRepository.findContractByOfferorAndRecipientAndStatus(
                offeror, recipient,Status.PENDING)).thenReturn(contract1);
        String ident = offeror.getPersonalData().getUserRole() + "-" +
                recipient.getPersonalData().getUserRole() + "-" + offeror.getPersonalData().getId();
        Contract contract = new Contract();
        contract.setIdentifier(ident);
        contract.setAmount(100);
        contract.setCurrency(Currency.BG);
        contract.setOfferor(offeror);
        contract.setRecipient(recipient);
        contract.setStatus(Status.PENDING);
        Contract response = contractService.createContract(1, 100, "BG", "B");
        verify(contractRepository, times(1)).save(response);
    }

    @Test
    void shouldThrowInvalidContractException() {
        LegalEntity offeror = formLegalEntity("A");
        LegalEntity recipient = formLegalEntity("A");
        when(legalEntityService.getLegalEntity(1)).thenReturn(offeror);
        when(legalEntityService.getLegalEntity("A")).thenReturn(recipient);
        Assertions.assertThrows(InvalidContractException.class, () -> {
            contractService.createContract(1, 100, "BG", "A");
        });
    }

    @Test
    void shouldThrowUniqueIdentificationNumberExistException() {
        LegalEntity offeror = formLegalEntity("A");
        LegalEntity recipient = formLegalEntity("B");
        Contract contract1 = formContract("", offeror, recipient);
        contract1.setStatus(Status.SIGNED);
        when(legalEntityService.getLegalEntity(1)).thenReturn(offeror);
        when(legalEntityService.getLegalEntity("B")).thenReturn(recipient);
        when(contractRepository.findContractByOfferorAndRecipientAndStatus(
                offeror, recipient,Status.SIGNED)).thenReturn(contract1);
        Assertions.assertThrows(UniqueIdentificationNumberExistException.class, () -> {
            contractService.createContract(1, 100, "BG", "B");
        });
    }

    @Test
    void shouldValidateContract() throws MessagingException {
        LegalEntity offeror = formLegalEntity("A");
        LegalEntity recipient = formLegalEntity("B");
        Contract contract = formContract("SHOP-DELIVER-1", offeror, recipient);
        recipient.setContractsToMe(List.of(contract));
        when(contractRepository.findContractByIdentifier("SHOP-DELIVER-1")).thenReturn(contract);
        when(legalEntityService.getLegalEntity(1)).thenReturn(recipient);
        Contract response = contractService.validateContract(1, "SHOP-DELIVER-1");
        Assertions.assertEquals(Status.SIGNED, response.getStatus());
        verify(contractRepository, times(1)).save(contract);
    }

    @Test
    void shouldThrowContractNotFoundExceptionWhenSearchForContract() {
        when(contractRepository.findContractByIdentifier("SHOP-DELIVER-1")).thenReturn(null);
        Assertions.assertThrows(ContractNotFoundException.class, () -> {
            contractService.validateContract(1, "");
        });
    }

    @Test
    void shouldThrowContractNotFoundExceptionWhenSearchInLegalEntityContractsList() {
        LegalEntity offeror = formLegalEntity("A");
        LegalEntity recipient = formLegalEntity("B");
        Contract contract = formContract("SHOP-DELIVER-1", offeror, recipient);
        when(contractRepository.findContractByIdentifier("SHOP-DELIVER-1")).thenReturn(contract);
        when(legalEntityService.getLegalEntity(1)).thenReturn(recipient);
        Assertions.assertThrows(ContractNotFoundException.class, () -> {
            contractService.validateContract(1, "");
        });
    }

    @Test
    void shouldThrowContractAlreadyValidatedException() {
        LegalEntity offeror = formLegalEntity("A");
        LegalEntity recipient = formLegalEntity("B");
        Contract contract = formContract("SHOP-DELIVER-1", offeror, recipient);
        recipient.setContractsToMe(List.of(contract));
        when(contractRepository.findContractByIdentifier("SHOP-DELIVER-1")).thenReturn(contract);
        when(legalEntityService.getLegalEntity(1)).thenReturn(recipient);
        contract.setStatus(Status.SIGNED);
        Assertions.assertThrows(ContractAlreadyValidatedException.class, () -> {
            contractService.validateContract(1, "SHOP-DELIVER-1");
        });
    }

    private Contract formContract(String identificator, LegalEntity offeror, LegalEntity recipient) {
        Contract contract = new Contract();
        contract.setIdentifier(identificator);
        contract.setAmount(100);
        contract.setCurrency(Currency.BG);
        contract.setOfferor(offeror);
        contract.setRecipient(recipient);
        contract.setStatus(Status.PENDING);
        return contract;
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
}