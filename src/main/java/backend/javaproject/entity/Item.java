package backend.javaproject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "items")
public class Item {
    //DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "itemName", nullable = false)
    private String itemName;
    
    @Column(name = "expiredDate")
    private String expiredDate;
    
    @Column(name = "purchasingDate")
    private String purchasingDate;
    
    @Column(name = "soldDate")
    private String soldDate;
    
    @Column(name = "wastedDate")
    private String wastedDate;
    @Column(name = "number")
    private int number;
    @Column(name = "location")
    private String location;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    @JsonBackReference
    private Store store;

    // Getters and setters

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getItemName() {
        return this.itemName;
    }
    public int getNumber() {
        return this.number;
    }
    public String getLocation() {
        return this.location;
    }
    public String getExpiredDate() {
        return this.expiredDate;
    }
    public String getPurchasingDate() {
        return this.purchasingDate;
    }
    public String getWastedDate() {
        return this.wastedDate;
    }
    public String getSoldDate() {
        return this.soldDate;
    }
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    public void setItemName(String itemName) {
        this.itemName=itemName;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate=expiredDate;
    }

    public void setPurchasingDate(String purchasingDate) {
        this.purchasingDate=purchasingDate;
    }

    public void setNumber(int number) {
        this.number=number;
    }

    public void setLocation(String location) {
        this.location=location;
    }
    public void setSoldDate(String soldDate) {
        this.soldDate=soldDate;
    }

    public void setWastedDate(String wastedDate) {
        this.wastedDate=wastedDate;
    }
    @Override
    public String toString() {
        return "Item: " + itemName +
            ", Expired Date: " + expiredDate +
            ", Purchasing Date: " + purchasingDate +
            ", Wasted Date: " + wastedDate+
            ", Sold Date: " + soldDate +
            ", Number: " + number +
            ", Location: " + location;
    }
}
