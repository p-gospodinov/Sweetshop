package bg.codeacademy.cakeShop.service;

import bg.codeacademy.cakeShop.model.Address;
import bg.codeacademy.cakeShop.model.BankAccount;
import bg.codeacademy.cakeShop.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReferenceService {
    private final BankAccountService bankAccountService;

    public ReferenceService(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    public List<BankAccount> getEarningReference(int id) {
        return bankAccountService.getBankAccounts(id);
    }
}
