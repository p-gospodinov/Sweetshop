package bg.codeacademy.cakeShop.service;

import bg.codeacademy.cakeShop.enums.Currency;
import bg.codeacademy.cakeShop.enums.Role;
import bg.codeacademy.cakeShop.error_handling.exception.UserNameExistException;
import bg.codeacademy.cakeShop.model.Address;
import bg.codeacademy.cakeShop.model.BankAccount;
import bg.codeacademy.cakeShop.model.PersonalData;
import bg.codeacademy.cakeShop.repository.PersonalDataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class PersonalDataServiceTest {
    static PersonalDataService personalDataService;
    static PersonalDataRepository personalDataRepository = mock(PersonalDataRepository.class);

    @BeforeAll
    public static void setup() {
        personalDataService = new PersonalDataService(personalDataRepository);
    }

    @Test
    void shouldSavePersonalData() {
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
        when(personalDataRepository.existsPersonalDataByUserName
                (personalData.getUserName())).thenReturn(false);
        String response = personalDataService.addPersonalData(personalData);
        Assertions.assertEquals(personalData.getUserName(), response);
        verify(personalDataRepository, times(1)).save(personalData);
    }

    @Test
    void shouldNotSavePersonalData() {
        PersonalData personalData = new PersonalData();
        personalData.setUserName("test");
        when(personalDataRepository.existsPersonalDataByUserName
                (anyString())).thenReturn(true);
        Assertions.assertThrows(UserNameExistException.class, () -> {
            personalDataService.addPersonalData(personalData);
        });
    }
}