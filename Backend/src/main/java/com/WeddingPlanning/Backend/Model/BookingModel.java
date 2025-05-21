package com.WeddingPlanning.Backend.Model;

public class Booking {
    private String serviceType;
    private double price;

    public Booking(String serviceType, double price) {
        this.serviceType = serviceType;
        this.price = price;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}