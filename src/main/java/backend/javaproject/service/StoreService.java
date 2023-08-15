package backend.javaproject.service;

import org.springframework.stereotype.Service;

import backend.javaproject.controller.LoginRequest;
import backend.javaproject.controller.LoginResponse;
import backend.javaproject.entity.Store;
import backend.javaproject.entity.StoreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public Store saveUser(Store store) throws Exception {
        String username = store.getUsername();
        Store existingStore = storeRepository.findByUsername(username);
        if (existingStore != null) {
            throw new Exception("Username already exists");
        }
        String encodedPassword = passwordEncoder.encode(store.getPassword());
        store.setPassword(encodedPassword);
        return storeRepository.save(store);
    }
    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        Store store = storeRepository.findByUsername(username);
        if (store != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(password, store.getPassword())) {
                String token = generateToken();
                Long store_id = store.getId();
                return new LoginResponse(token, store,store_id);
            }
        }
        return null; // Authentication failed
    }
    private String generateToken() {
        return "generated_token";
    }
}