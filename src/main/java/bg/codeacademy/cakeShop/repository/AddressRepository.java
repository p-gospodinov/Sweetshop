package bg.codeacademy.cakeShop.repository;

import bg.codeacademy.cakeShop.model.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address, Integer> {
    boolean existsAddressByCityAndStreet(String city, String street);
    Address findAddressByCityAndStreet(String city, String street);
}
