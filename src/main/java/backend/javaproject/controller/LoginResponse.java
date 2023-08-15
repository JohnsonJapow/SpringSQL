package backend.javaproject.controller;

import backend.javaproject.entity.Store;

public class LoginResponse {
    private String token;
    private Store store;
    private Long store_id;
    // Constructor, getters and setters

    public LoginResponse(String token, Store store,Long store_id) {
        this.token = token;
        this.store = store;
        this.store_id=store_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
    public Long getStoreId(){
        return store_id;
    }
    @Override
    public String toString() {
        return "LoginResponse [store=" + store + ", token=" + token + ", storeId=" + store_id + "]";
    }
}