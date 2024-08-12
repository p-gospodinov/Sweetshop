package bg.codeacademy.cakeShop.repository;

import bg.codeacademy.cakeShop.model.PersonalData;
import org.springframework.data.repository.CrudRepository;

public interface PersonalDataRepository extends CrudRepository<PersonalData, Integer> {
    boolean existsPersonalDataByUserName(String userName);
    PersonalData findPersonalDataByUserName(String userName);
}
