package bg.codeacademy.cakeShop.repository;

import bg.codeacademy.cakeShop.model.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<Storage, Integer> {
    Storage findByItem_NameAndOwner_Id(String name, int id);
}

