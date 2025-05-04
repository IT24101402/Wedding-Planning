package com.WeddingPlanning.Backend.Model;

public class Vendor  extends User{
    private String serviceType;

    // Constructor, getters,setters


    public Vendor(String id, String username, String email, String password, String phone) {
        super(id, username, email, password, phone);
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
