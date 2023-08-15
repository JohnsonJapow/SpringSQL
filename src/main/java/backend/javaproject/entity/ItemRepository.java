    package backend.javaproject.entity;

    import java.util.List;

    import org.springframework.data.jpa.repository.JpaRepository;

    public interface ItemRepository extends JpaRepository<Item, Long> {
        List<Item> findByStore(Store store);
        List<Item> findByItemNameAndStore(String itemName, Store store);
        List<Item> findByItemName(String itemName);
    }
