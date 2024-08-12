package bg.codeacademy.cakeShop.service;

import bg.codeacademy.cakeShop.error_handling.exception.ItemStockException;
import bg.codeacademy.cakeShop.error_handling.exception.NoContractException;
import bg.codeacademy.cakeShop.error_handling.exception.StorageNotExistException;
import bg.codeacademy.cakeShop.model.Contract;
import bg.codeacademy.cakeShop.model.Item;
import bg.codeacademy.cakeShop.model.Storage;
import bg.codeacademy.cakeShop.repository.StorageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class StorageService {
    private final StorageRepository storageRepository;
    private final ItemService itemService;
    private final LegalEntityService legalEntityService;
    private final ContractService contractService;
    private final TransactionService transactionService;

    @Autowired
    public StorageService(
            StorageRepository storageRepository,
            ItemService itemService,
            LegalEntityService legalEntityService, ContractService contractService, TransactionService transactionService) {
        this.storageRepository = storageRepository;
        this.itemService = itemService;
        this.legalEntityService = legalEntityService;
        this.contractService = contractService;
        this.transactionService = transactionService;
    }

    @Transactional
    public Map<Item, Integer> createItemInStorage(int principalId, Map<Item, Integer> items) {
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            Optional<Storage> existingStorage = Optional.
                    ofNullable(storageRepository.findByItem_NameAndOwner_Id(entry.getKey().getName(), principalId));
            if (existingStorage.isPresent()) {
                Storage storage = existingStorage.get();
                storage.setCount(storage.getCount() + entry.getValue());
                storageRepository.save(storage);
                log.info("Service | Update item with name:" + entry.getKey().getName());
            } else {
                Item item = itemService.createItem(entry.getKey().getName(), entry.getKey().getPrice());
                Storage newStorage = new Storage();
                newStorage.setItem(item);
                newStorage.setCount(entry.getValue());
                newStorage.setOwner(legalEntityService.getLegalEntity(principalId));
                storageRepository.save(newStorage);
                log.info("Service | Create an item with name:" + entry.getKey());
            }
        }
        return items;
    }

    @Transactional
    public Map<String, Integer> putItemInShopStorage(int principalId, int deliveryId,
                                                      Map<String, Integer> itemsNameList) {
        Contract contract = contractService.getValidatedContract(principalId, deliveryId);
        if (contract == null) {
            log.error("Service | Not allowed to buy items from deliver with ID:" + deliveryId +
                    " because of missing contract!");
            throw new NoContractException("Not allowed to buy items from deliver with ID:" + deliveryId +
                    " because of missing contract!");
        }
        float totalOrderSum = 0;
        Map<Item, Integer> itemsList = new HashMap<>();
        for (Map.Entry<String, Integer> entry : itemsNameList.entrySet()) {
            Storage deliveryStorageRow = storageRepository.findByItem_NameAndOwner_Id(
                    entry.getKey(), deliveryId);
            if (deliveryStorageRow == null) {
                log.error("Service | No record for item with name:" + entry.getKey() +
                        " in storage with ID:" + deliveryId);
                throw new StorageNotExistException("No record for item with name:" + entry.getKey() +
                        " in storage with ID:" + deliveryId);
            }
            int itemCount = entry.getValue();
            int amount = deliveryStorageRow.getCount();
            int finalAmount = amount - itemCount;
            if (finalAmount < 0) {
                log.error("Service | The stock of item:" + entry.getKey()
                        + " is not enough to transfer it to the shop storage!");
                throw new ItemStockException("The stock of item:" + entry.getKey()
                        + " is not enough to transfer it to the shop storage!");
            }
            deliveryStorageRow.setCount(finalAmount);
            //Here update the delivery storage
            storageRepository.save(deliveryStorageRow);
            log.info("Service | Update item with name:" + entry.getKey());

            itemsList.put(deliveryStorageRow.getItem(), itemCount);
            totalOrderSum += deliveryStorageRow.getItem().getPrice() * entry.getValue();
        }
        //Here update the principal storage
        createItemInStorage(principalId, itemsList);
        return itemsNameList;
    }

    public Storage getStorage(String itemName, int shopId) {
        Storage storageRow = storageRepository.findByItem_NameAndOwner_Id(itemName, shopId);
        if (storageRow == null) {
            log.error("Service | No item with name:" +
                    itemName + " in storage with legal-entity ID:" + shopId);
            throw new ItemStockException("No item with name:" +
                    itemName + " in storage with legal-entity ID:" + shopId);
        }
        log.info("Service | Get item with name:" +
                itemName + " from storage with legal-entity ID:" + shopId);
        return storageRow;
    }

    public void updateStorage(Storage storageRow) {
        log.info("Service | Update storage");
        storageRepository.save(storageRow);
    }
}
