package bg.codeacademy.cakeShop.repository;

import bg.codeacademy.cakeShop.model.Staff;
import org.springframework.data.repository.CrudRepository;

public interface StaffRepository extends CrudRepository<Staff, Integer> {
}
