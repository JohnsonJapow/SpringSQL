package backend.javaproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.javaproject.entity.Item;
import backend.javaproject.entity.ItemRepository;
import backend.javaproject.entity.Store;
import backend.javaproject.entity.StoreRepository;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private StoreRepository storeRepository;
    public Item getItemById(Long itemId) {
        return itemRepository.findById(itemId)
            .orElseThrow(() -> new RuntimeException("Item not found with id " + itemId));
    }
    public List<Item> getItemsByNameAndStore(Long storeId, String itemName) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new RuntimeException("Store not found with id " + storeId));
        List<Item> items = itemRepository.findByItemNameAndStore(itemName, store);
        return items;
    }
    public List<Item> getAllItemsByStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new RuntimeException("Store not found with id " + storeId));
        return itemRepository.findByStore(store);
    }
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
    public Item addItem(Item item, Long store_id) {
        // Find the store by its ID
        Store store = storeRepository.findById(store_id)
        .orElseThrow(() -> new RuntimeException("Store not found with id " + store_id));
        // Check if an item with the same details already exists
        List<Item> existingItems = itemRepository.findByItemNameAndStore(item.getItemName()
        , store);
        for (Item existingItem : existingItems) {
            if (existingItem.getItemName().equals(item.getItemName()) &&
                existingItem.getPurchasingDate().equals(item.getPurchasingDate()) &&
                existingItem.getExpiredDate().equals(item.getExpiredDate()) &&
                existingItem.getLocation().equals(item.getLocation())) {
                // If an item with the same details exists, update the number of items and return
                existingItem.setNumber(existingItem.getNumber() + item.getNumber());
                return itemRepository.save(existingItem);
            }
        }
        // Set the store for the item
        item.setStore(store);
        return itemRepository.save(item);
    }
    public Item updateItem(Long storeId, Long itemId, Item itemDetails) {
        // Find the store by its ID
        Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new RuntimeException("Store not found with id " + storeId));
        // Find the item by its ID
        Item existingItem = itemRepository.findById(itemId)
        .orElseThrow(() -> new RuntimeException("Item not found with id " + itemId));
        // Check if the item belongs to the given store
        if (!existingItem.getStore().getId().equals(storeId)) {
            throw new RuntimeException("Mismatch between the item's store and the provided store id.");
        }
        if (itemDetails.getSoldDate() != null || itemDetails.getWastedDate() != null) {
            // Reduce the number of items in the existing item
            int updatedNumber = existingItem.getNumber() - itemDetails.getNumber();
            if (updatedNumber < 0) {
                throw new RuntimeException("Insufficient quantity of item id " + itemId + " in store id " + storeId);
            }
            existingItem.setNumber(updatedNumber);
            // Create a new item with the sold or wasted details
            Item newItem = new Item();
            newItem.setItemName(itemDetails.getItemName());
            newItem.setNumber(itemDetails.getNumber());
            newItem.setLocation(itemDetails.getLocation());
            newItem.setExpiredDate(itemDetails.getExpiredDate());
            newItem.setPurchasingDate(itemDetails.getPurchasingDate());
            newItem.setSoldDate(itemDetails.getSoldDate());
            newItem.setWastedDate(itemDetails.getWastedDate());
            newItem.setStore(store);
            // Save the updated existing item and the new item
            itemRepository.save(existingItem);
            return itemRepository.save(newItem);
        }
        // If no soldDate or wastedDate is found, update the item as usual
        existingItem.setItemName(itemDetails.getItemName());
        existingItem.setNumber(itemDetails.getNumber());
        existingItem.setLocation(itemDetails.getLocation());
        existingItem.setExpiredDate(itemDetails.getExpiredDate());
        existingItem.setPurchasingDate(itemDetails.getPurchasingDate());
        return itemRepository.save(existingItem);
    }    
}