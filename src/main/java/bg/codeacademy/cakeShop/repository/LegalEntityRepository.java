package bg.codeacademy.cakeShop.repository;

import bg.codeacademy.cakeShop.model.Address;
import bg.codeacademy.cakeShop.model.LegalEntity;
import bg.codeacademy.cakeShop.model.PersonalData;
import org.springframework.data.repository.CrudRepository;

public interface LegalEntityRepository extends CrudRepository<LegalEntity, Integer> {
    boolean existsLegalEntityByUin(String uin);
    LegalEntity findLegalEntityByPersonalData_id(int id);

    LegalEntity findLegalEntityByUin(String uin);
}
