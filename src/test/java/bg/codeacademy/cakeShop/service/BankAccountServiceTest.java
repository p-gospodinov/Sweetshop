package bg.codeacademy.cakeShop.service;

import bg.codeacademy.cakeShop.enums.BankAccountType;
import bg.codeacademy.cakeShop.enums.Currency;
import bg.codeacademy.cakeShop.error_handling.exception.BankAccountExistException;
import bg.codeacademy.cakeShop.error_handling.exception.BankAccountNotExistException;
import bg.codeacademy.cakeShop.model.Address;
import bg.codeacademy.cakeShop.model.BankAccount;
import bg.codeacademy.cakeShop.model.PersonalData;
import bg.codeacademy.cakeShop.repository.BankAccountRepository;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.mockito.Mockito.*;

class BankAccountServiceTest {
    static BankAccountService bankAccountService;
    static PersonalDataService personalDataService = mock(PersonalDataService.class);
    static BankAccountRepository bankAccountRepository = mock(BankAccountRepository.class);

    @Test
    void shouldAddBankAccount() {
        bankAccountService = new BankAccountService(bankAccountRepository, personalDataService);
        BankAccount account = new BankAccount();
        account.setIban("BG18RZBB91550123456789");
        account.setCurrency(Currency.BG);
        when(bankAccountRepository.existsBankAccountByIban(account.getIban()))
                .thenReturn(false);
        List<String> iban = bankAccountService.createBankAccount(List.of(account));
        Assertions.assertEquals(iban.get(0), account.getIban());
        verify(bankAccountRepository, times(1)).save(account);
    }

    @Test
    void shouldGetBankAccount() {
        bankAccountService = new BankAccountService(bankAccountRepository, personalDataService);
        BankAccount account = new BankAccount();
        account.setIban("BG18RZBB91550123456789");
        account.setCurrency(Currency.BG);
        when(bankAccountRepository.findBankAccountByBeneficiary_idAndIban(
                1, "BG18RZBB91550123456789")).thenReturn(account);
        BankAccount response = bankAccountService.getBankAccount(1, "BG18RZBB91550123456789");
        Assertions.assertEquals(account, response);
    }

    @Test
    void shouldThrowBankAccountNotExistExceptionWhenGetBankAccount() {
        bankAccountService = new BankAccountService(bankAccountRepository, personalDataService);
        BankAccount account = new BankAccount();
        account.setIban("BG18RZBB91550123456789");
        account.setCurrency(Currency.BG);
        when(bankAccountRepository.findBankAccountByBeneficiary_idAndIban(
                1, "BG18RZBB91550123456789")).thenReturn(account);
        Assert.assertThrows(BankAccountNotExistException.class, () -> {
            bankAccountService.getBankAccount(1, "notExistIban");
        });
    }

    @Test
    void shouldThrowBankAccountExistExceptionWhenAddBankAccount() {
        bankAccountService = new BankAccountService(bankAccountRepository, personalDataService);
        BankAccount account = new BankAccount();
        account.setIban("BG18RZBB91550123456789");
        account.setCurrency(Currency.BG);
        when(bankAccountRepository.existsBankAccountByIban(account.getIban()))
                .thenReturn(true);
        Assertions.assertThrows(BankAccountExistException.class, () -> {
            bankAccountService.createBankAccount(List.of(account));
        });
    }

    @Test
    void shouldDeleteBankAccount() {
        bankAccountService = new BankAccountService(bankAccountRepository, personalDataService);
        BankAccount account = new BankAccount();
        account.setIban("BG18RZBB91550123456789");
        account.setCurrency(Currency.BG);
        when(bankAccountRepository.findBankAccountByBeneficiary_idAndIban(
                1, "BG18RZBB91550123456789")).thenReturn(account);
        bankAccountService.deleteBankAccount(1, "BG18RZBB91550123456789");
        verify(bankAccountRepository).delete(account);
    }

    @Test
    void shouldChangeBankAccountCurrency() {
        BankAccountRepository bankAccountRepository = mock(BankAccountRepository.class);
        bankAccountService = new BankAccountService(bankAccountRepository, personalDataService);
        BankAccount account = new BankAccount();
        account.setIban("BG18RZBB91550123456789");
        account.setCurrency(Currency.BG);
        when(bankAccountRepository.findBankAccountByBeneficiary_idAndIban(
                1, "BG18RZBB91550123456789")).thenReturn(account);
        BankAccount response = bankAccountService.changeBankAccountCurrency(
                1,
                "BG18RZBB91550123456789",
                String.valueOf(Currency.EU));
        Assertions.assertEquals(Currency.EU, response.getCurrency());
        verify(bankAccountRepository).save(account);
    }

    @Test
    void shouldCreateBankAccount() {
        BankAccountRepository bankAccountRepository = mock(BankAccountRepository.class);
        bankAccountService = new BankAccountService(bankAccountRepository, personalDataService);
        BankAccount account = new BankAccount();
        account.setIban("BG18RZBB91550123456789");
        PersonalData recipient = formPersonalData(1, account);
        when(personalDataService.getPersonalData(1)).thenReturn(recipient);
        when(bankAccountRepository.existsBankAccountByIban("BG18RZBB91550123456789")).thenReturn(false);
        BankAccount response = bankAccountService.createBankAccount(
                1,
                "BG18RZBB91550123456789",
                200,
                String.valueOf(Currency.BG),
                BankAccountType.GENERAL
        );
        verify(bankAccountRepository).save(response);
    }

    private PersonalData formPersonalData(int id, BankAccount bankAccount) {
        Address address = new Address();
        address.setCity("city");
        address.setStreet("street");
        PersonalData personalData = new PersonalData();
        personalData.setUserName("test");
        personalData.setAddress(address);
        personalData.setBankAccount(List.of(bankAccount));
        personalData.setUserPassword("password");
        personalData.setPersonalName("personalName");
        return personalData;
    }
}