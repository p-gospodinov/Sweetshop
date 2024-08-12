package bg.codeacademy.cakeShop.service;

import bg.codeacademy.cakeShop.model.Item;
import bg.codeacademy.cakeShop.repository.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item createItem(String name, float price) {
        if (!itemRepository.existsItemByName(name)) {
            Item item = new Item();
            item.setName(name);
            item.setPrice(price);
            itemRepository.save(item);
            return item;
        }
        return itemRepository.findItemByName(name);
    }
}