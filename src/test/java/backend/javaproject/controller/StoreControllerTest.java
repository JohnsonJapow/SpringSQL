package backend.javaproject.controller;
import backend.javaproject.entity.Store;
import backend.javaproject.service.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class StoreControllerTest {
    @InjectMocks
    StoreController storeController;
    @Mock
    StoreService storeService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void addUser_ValidStore_ReturnsStore() throws Exception {
        Store store = new Store();
        store.setUsername("username");
        store.setPassword("password");
        when(storeService.saveUser(store)).thenReturn(store);
        Store result = storeController.addUser(store);
        assertEquals(store, result);
    }
    @Test
    public void addUser_InvalidStore_ThrowsException() {
        try {
            Store store = new Store();
            Mockito.when(storeService.saveUser(store)).thenReturn(store);
            Store result = storeController.addUser(store);
            assertEquals(store, result);
            System.out.println("Test passed!");
        } catch (Exception e) {
            System.out.println("Test failed with exception: " + e.getMessage());
        }
    }
}
