package bg.codeacademy.cakeShop.service;

import bg.codeacademy.cakeShop.enums.BankAccountType;
import bg.codeacademy.cakeShop.enums.Currency;
import bg.codeacademy.cakeShop.error_handling.exception.BankAccountExistException;
import bg.codeacademy.cakeShop.error_handling.exception.BankAccountNotExistException;
import bg.codeacademy.cakeShop.model.BankAccount;
import bg.codeacademy.cakeShop.model.PersonalData;
import bg.codeacademy.cakeShop.repository.BankAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final PersonalDataService personalDataService;

    public BankAccountService(BankAccountRepository bankAccountRepository, PersonalDataService personalDataService) {
        this.bankAccountRepository = bankAccountRepository;
        this.personalDataService = personalDataService;
    }

    public List<String> createBankAccount(List<BankAccount> bankAccounts) {
        List<String> info = new LinkedList<>();
        for (BankAccount account : bankAccounts) {
            saveBankAccount(account);
            info.add(account.getIban());
        }
        log.info("Service | Create one or more bank accounts for legal-entity or staff");
        return info;
    }

    private void saveBankAccount(BankAccount account) {
        if (bankAccountRepository.existsBankAccountByIban(account.getIban())) {
            log.error("Service | BankAccountExistException(Bank account with iban:"
                    + account.getIban() + " is already in use!)");
            throw new BankAccountExistException("Bank account with iban:"
                    + account.getIban() + " is already in use!");
        }
        log.info("Service | Save bank account with IBAN:" + account.getIban());
        bankAccountRepository.save(account);
    }

    public BankAccount getBankAccount(int id, String iban) {
        BankAccount account = bankAccountRepository.findBankAccountByBeneficiary_idAndIban(id, iban);
        if (account == null) {
            log.error("Service | Not found bank account with IBAN:" + iban);
            throw new BankAccountNotExistException("Not found bank account with IBAN:" + iban);
        }
        log.info("Service | Save bank account with IBAN:" + account.getIban());
        return account;
    }

    public BankAccount getBankAccount(String iban) {
        BankAccount account = bankAccountRepository.findBankAccountByIban(iban);
        if (account == null) {
            log.error("Service | Not found bank account with IBAN:" + iban);
            throw new BankAccountNotExistException("Not found bank account with IBAN:" + iban);
        }
        log.info("Service | Get bank account with IBAN:" + iban);
        return account;
    }

    public BankAccount update(BankAccount account) {
        boolean isExist = bankAccountRepository.existsById(account.getId());
        if (!isExist) {
            log.error("Service | Not found bank account with ID:" + account.getId());
            throw new BankAccountNotExistException("Not found bank account with ID:" + account.getId());
        }
        bankAccountRepository.save(account);
        log.info("Service | Update bank account with IBAN:" + account.getIban());
        return account;
    }

    public BankAccount deleteBankAccount(int principalId, String iban) {
        BankAccount account = getBankAccount(principalId, iban);
        bankAccountRepository.delete(account);
        log.info("Service | Delete bank account with IBAN:" + iban);
        return account;
    }

    public BankAccount changeBankAccountCurrency(int principalId, String iban, String currency) {
        BankAccount account = getBankAccount(principalId, iban);
        account.setCurrency(Currency.valueOf(currency));
        bankAccountRepository.save(account);
        log.info("Service | Change bank account currency with IBAN:" + iban + " to " + currency);
        return account;
    }

    public BankAccount createBankAccount(
            int id,
            String iban,
            float amount,
            String currency,
            BankAccountType bankAccountType) {
        PersonalData personalData = personalDataService.getPersonalData(id);
        BankAccount account = new BankAccount();
        account.setIban(iban);
        account.setAmount(amount);
        account.setCurrency(Currency.valueOf(currency));
        account.setBeneficiary(personalData);
        account.setBankAccountType(bankAccountType);
        saveBankAccount(account);
        return account;
    }

    public List<BankAccount> getBankAccounts(int id) {
        log.info("Service | Get all bank accounts");
        return personalDataService.getPersonalData(id).getBankAccount();
    }

    public BankAccount getBankAccount(int id, BankAccountType bankAccountType) {
        for (BankAccount ba : getBankAccounts(id)) {
            if (ba.getBankAccountType().equals(bankAccountType)) {
                return ba;
            }
        }
        log.error("Service | Bank account of type:" + bankAccountType + " not found!");
        throw new BankAccountNotExistException("Bank account of type:" + bankAccountType + " not found!");
    }
}
