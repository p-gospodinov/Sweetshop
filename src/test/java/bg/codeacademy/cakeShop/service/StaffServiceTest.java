package bg.codeacademy.cakeShop.service;

import bg.codeacademy.cakeShop.enums.Currency;
import bg.codeacademy.cakeShop.enums.Role;
import bg.codeacademy.cakeShop.error_handling.exception.RoleNotSupportedException;
import bg.codeacademy.cakeShop.model.*;
import bg.codeacademy.cakeShop.repository.StaffRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

class StaffServiceTest {

    private static StaffService staffService;
    private static StaffRepository staffRepository;
    private static AddressService addressService;
    private static BankAccountService bankAccountService;
    private static PersonalDataService personalDataService;
    private static LegalEntityService legalEntityService;
    private static final List<String> staffRoles =
            new LinkedList<>(List.of("MANAGER", "WORKER"));
    private static final List<String> legalUserRole =
            new LinkedList<>(List.of("SHOP", "RENTIER", "DELIVER"));

    @BeforeEach
    public void setup() {
        staffRepository = mock(StaffRepository.class);
        addressService = mock(AddressService.class);
        bankAccountService = mock(BankAccountService.class);
        personalDataService = mock(PersonalDataService.class);
        legalEntityService = mock(LegalEntityService.class);
        staffService = new StaffService(addressService
                , bankAccountService
                , personalDataService
                , legalEntityService
                , staffRepository);
        ReflectionTestUtils.setField(staffService, "roles", staffRoles);
    }

    @Test
    void shouldAddStaff() {
        Staff staff = formStaff();
        LegalEntity legalEntity = formLegalEntity();
        for (int i = 0; i < staffRoles.size(); i++) {
            staff.getPersonalData().setUserRole(Role.valueOf(staffRoles.get(i)));

            when(addressService.addAddress(any(Address.class)))
                    .thenReturn(staff.getPersonalData().getAddress());
            when(legalEntityService.getLegalEntity(any(Integer.class)))
                    .thenReturn(legalEntity);

            Staff response = staffService.createStaff(staff.getPersonalData(), 4);
            verify(addressService, times(i + 1))
                    .addAddress(staff.getPersonalData().getAddress());
            verify(personalDataService
                    , times(i + 1)).addPersonalData(staff.getPersonalData());
            verify(bankAccountService
                    , times(i + 1)).createBankAccount(staff.getPersonalData().getBankAccount());
            verify(legalEntityService
                    , times(i + 1)).getLegalEntity(4);

            verify(staffRepository, times(1)).save(response);
        }
    }

    @Test
    void shouldThrowRoleNotSupportedException() {
        Staff staff = formStaff();
        staff.setEmployer(formLegalEntity());
        for (String legalUserRole : legalUserRole) {
            staff.getPersonalData().setUserRole(Role.valueOf(legalUserRole));
            Assertions.assertThrows(RoleNotSupportedException.class, () -> {
                staffService.createStaff(staff.getPersonalData()
                        , 4);
            });
        }
    }

    private Staff formStaff() {
        Address address = new Address();
        address.setCity("city");
        address.setStreet("street");
        BankAccount account = new BankAccount();
        account.setIban("BG18RZBB91550123456789");
        account.setCurrency(Currency.BG);
        PersonalData personalData = new PersonalData();
        personalData.setUserName("test");
        personalData.setUserRole(Role.DELIVER);
        personalData.setAddress(address);
        personalData.setBankAccount(List.of(account));
        personalData.setUserPassword("password");
        personalData.setPersonalName("personalName");
        Staff staff = new Staff();
        staff.setPersonalData(personalData);
        return staff;
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
