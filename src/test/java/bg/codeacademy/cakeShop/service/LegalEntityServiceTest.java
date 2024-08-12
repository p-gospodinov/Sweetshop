package bg.codeacademy.cakeShop.service;

import bg.codeacademy.cakeShop.enums.Currency;
import bg.codeacademy.cakeShop.enums.Role;
import bg.codeacademy.cakeShop.error_handling.exception.RoleNotSupportedException;
import bg.codeacademy.cakeShop.error_handling.exception.UniqueIdentificationNumberExistException;
import bg.codeacademy.cakeShop.model.Address;
import bg.codeacademy.cakeShop.model.BankAccount;
import bg.codeacademy.cakeShop.model.LegalEntity;
import bg.codeacademy.cakeShop.model.PersonalData;
import bg.codeacademy.cakeShop.repository.LegalEntityRepository;
import org.junit.jupiter.api.*;
import org.springframework.test.util.ReflectionTestUtils;
import javax.naming.OperationNotSupportedException;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

class LegalEntityServiceTest {
    static LegalEntityService legalEntityService;
    private static final AddressService addressService = mock(AddressService.class);
    private static final BankAccountService bankAccountService = mock(BankAccountService.class);
    private static final PersonalDataService personalDataService = mock(PersonalDataService.class);
    private final List<String> legalEntityRoles = new LinkedList<>(List.of("SHOP", "RENTIER", "DELIVER"));
    private final List<String> staffRoles = new LinkedList<>(List.of("MANAGER", "WORKER"));
    static LegalEntityRepository legalEntityRepository = mock(LegalEntityRepository.class);

    @BeforeAll
    public static void setup() {
        legalEntityService = new LegalEntityService(legalEntityRepository
                , addressService
                , bankAccountService
                , personalDataService);
    }

    @Test
    void shouldSaveLegalEntity() {
        ReflectionTestUtils.setField(legalEntityService, "roles", legalEntityRoles);
        LegalEntity legalEntity = formLegalEntity();
        for (int i = 0; i < legalEntityRoles.size(); i++) {
            legalEntity.getPersonalData().setUserRole(Role.valueOf(legalEntityRoles.get(i)));
            when(legalEntityRepository.existsLegalEntityByUin("UIN")
            ).thenReturn(false);
            when(legalEntityRepository.save(legalEntity)).thenReturn(legalEntity);
            LegalEntity response = legalEntityService.createLegalEntity(legalEntity);
            Assertions.assertEquals(legalEntity, response);
            verify(legalEntityRepository, times(i + 1)).save(legalEntity);
        }
    }

    @Test
    void shouldThrowUniqueIdentificationNumberExistException() {
        ReflectionTestUtils.setField(legalEntityService, "roles", legalEntityRoles);
        LegalEntity legalEntity = formLegalEntity();
        legalEntity.setUin("UIN");
        legalEntity.getPersonalData().setUserRole(Role.valueOf(legalEntityRoles.get(0)));
        when(legalEntityRepository.existsLegalEntityByUin("UIN")
        ).thenReturn(true);
        Assertions.assertThrows(UniqueIdentificationNumberExistException.class, () -> {
            legalEntityService.createLegalEntity(legalEntity);
        });
    }

    @Test
    void shouldThrowRoleNotSupportedException() throws OperationNotSupportedException {
        ReflectionTestUtils.setField(legalEntityService, "roles", legalEntityRoles);
        LegalEntity legalEntity = formLegalEntity();
        for (String staffRole : staffRoles) {
            legalEntity.getPersonalData().setUserRole(Role.valueOf(staffRole));
            Assertions.assertThrows(RoleNotSupportedException.class, () -> {
                legalEntityService.createLegalEntity(legalEntity);
            });
        }
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