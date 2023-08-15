package backend.javaproject.controller;

import org.springframework.web.bind.annotation.RestController;

import backend.javaproject.entity.Store;
import backend.javaproject.service.StoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class StoreController {
    
    @Autowired
    private StoreService storeService;

    @PostMapping("/users")
    public Store addUser(@RequestBody Store store) throws Exception {
        return storeService.saveUser(store);
    }
}
