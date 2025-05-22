package com.WeddingPlanning.Backend.Model;

public class Reservation {
    private String customerName;
    private String vendorName;
    private String serviceType;
    private double price;

    public Reservation(String customerName, String vendorName, String serviceType, double price) {
        this.customerName = customerName;
        this.vendorName = vendorName;
        this.serviceType = serviceType;
        this.price = price;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
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

    @Override
    public String toString() {
        return customerName + "," + vendorName + "," + serviceType + "," + price;
    }

    public static Reservation fromString(String line) {
        String[] parts = line.split(",");
        String customerName = parts[0];
        String vendorName = parts[1];
        String serviceType = parts[2];
        double price = Double.parseDouble(parts[3]);
        return new Reservation(customerName, vendorName, serviceType, price);
    }

}