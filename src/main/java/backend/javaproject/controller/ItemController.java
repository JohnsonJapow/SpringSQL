package backend.javaproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.javaproject.entity.Item;
import backend.javaproject.service.ItemService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class ItemController {

    @Autowired
    private ItemService itemService;


    @GetMapping("/stores/{storeId}/items/{itemName}")
    public ResponseEntity<?> getItemsByNameAndStore(@PathVariable Long storeId, @PathVariable String itemName) {
        try {
            List<Item> items = itemService.getItemsByNameAndStore(storeId, itemName);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get items");
        }
    }

    @GetMapping("/items")
    public ResponseEntity<?> getAllItems() {
        try {
            List<Item> items = itemService.getAllItems();
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get items");
        }
    }

    @PostMapping("/items")
    public ResponseEntity<?> addItem(@RequestBody Item item,@RequestParam Long store_id) {
        System.out.println("Received item: " + item.toString());
        if (item.getItemName() == null || item.getItemName().isEmpty()) {
            return ResponseEntity.badRequest().body("Item name cannot be empty");
        }

        try {
            Item newItem = itemService.addItem(item,store_id);
            return ResponseEntity.ok(newItem);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data integrity violation - perhaps this item already exists?");
        } catch (OptimisticLockingFailureException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict - this item was updated by another request.");
        } catch (Exception e) {
            e.printStackTrace();  // This will print detailed error message to the console
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add new item");
        }
        

    }
    @PutMapping("/stores/{storeId}/items/{itemId}")
    public ResponseEntity<?> updateItem(@PathVariable Long storeId, @PathVariable Long itemId, @RequestBody Item item) {
        try {
            System.out.println("itemid:"+itemId+"item:"+item);
            Item updatedItem = itemService.updateItem(storeId, itemId, item);
            return ResponseEntity.ok(updatedItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update item");
        }
    }


    @GetMapping("/stores/{storeId}/items")
    public ResponseEntity<?> getAllItemsByStore(@PathVariable Long storeId) {
        try {
            List<Item> items = itemService.getAllItemsByStore(storeId);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get items for store " + storeId);
        }
    }

    @GetMapping("/items/{itemId}")
    public ResponseEntity<?> getItemById(@PathVariable Long itemId) {
        try {
            Item item = itemService.getItemById(itemId); // You will also need to implement this method in your service
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get item");
        }
    }

}