package bg.codeacademy.cakeShop.repository;

import bg.codeacademy.cakeShop.model.Address;
import bg.codeacademy.cakeShop.model.LegalEntity;
import bg.codeacademy.cakeShop.model.Offer;
import org.springframework.data.repository.CrudRepository;

public interface OfferRepository extends CrudRepository<Offer, Integer> {
    boolean existsOfferByOfferorAndMoney(LegalEntity offeror, float money);

}
