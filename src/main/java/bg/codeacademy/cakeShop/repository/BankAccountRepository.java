package bg.codeacademy.cakeShop.repository;

import bg.codeacademy.cakeShop.model.BankAccount;
import org.springframework.data.repository.CrudRepository;

public interface BankAccountRepository extends CrudRepository<BankAccount, Integer> {
    boolean existsBankAccountByIban(String iban);

    BankAccount findBankAccountByBeneficiary_idAndIban(int principalId, String iban);

    BankAccount findBankAccountByIban(String iban);

    BankAccount findBankAccountByIbanAndId(String iban, int id);
}
