package com.WeddingPlanning.Backend.Model;

public class Booking {
    private String bookingId;
    private String userName;
    private String vendorName;
    private String serviceType; // e.g., Photography, Catering
    private String bookingDate;
    private String bookingTime;

    public Booking() {
    }

    public Booking(String bookingId, String userName, String vendorName, String serviceType, String bookingDate, String bookingTime) {
        this.bookingId = bookingId;
        this.userName = userName;
        this.vendorName = vendorName;
        this.serviceType = serviceType;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
    }

    // Getters and Setters
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    // Polymorphic Confirmation
    public String getConfirmationMessage() {
        return "Booking confirmed for " + serviceType + " with " + vendorName + " on " + bookingDate + " at " + bookingTime + ".";
    }

    @Override
    public String toString() {
        return bookingId + "," + userName + "," + vendorName + "," + serviceType + "," + bookingDate + "," + bookingTime;
    }

    public static Booking fromString(String line) {
        String[] parts = line.split(",");
        return new Booking(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
    }
}