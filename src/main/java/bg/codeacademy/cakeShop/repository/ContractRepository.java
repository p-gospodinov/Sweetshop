package bg.codeacademy.cakeShop.repository;

import bg.codeacademy.cakeShop.enums.Role;
import bg.codeacademy.cakeShop.enums.Status;
import bg.codeacademy.cakeShop.model.Contract;
import bg.codeacademy.cakeShop.model.LegalEntity;
import org.springframework.data.repository.CrudRepository;

public interface ContractRepository extends CrudRepository<Contract, Integer> {

    Contract findContractByOfferorAndRecipientAndStatus(LegalEntity offeror, LegalEntity recipient, Status status);

    Contract findContractByIdentifier(String identifier);

    Contract findByOfferor_IdAndStatusAndRecipient_PersonalData_UserRole(int shopId, Status status, Role role);
}
