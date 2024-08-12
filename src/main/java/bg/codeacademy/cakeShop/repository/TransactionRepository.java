package bg.codeacademy.cakeShop.repository;

import bg.codeacademy.cakeShop.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    //boolean existsAddressByCityAndStreet(String city, String street);
    ///Address findAddressByCityAndStreet(String city, String street);
}
