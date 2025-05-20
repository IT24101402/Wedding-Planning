package com.WeddingPlanning.Backend.Model;

public class Vendor {
    protected Long id;
    protected String name;
    protected String category;
    protected double price;
    protected String contact;
    protected String email;
    protected String password;

    public Vendor(Long id, String name, String category, double price, String contact, String email, String password) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.contact = contact;
        this.email = email;
        this.password = password;
    }

    // Getters and setters for encapsulation
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toFileString() {
        return id + "," + name + "," + category + "," + price + "," + contact + "," + email + "," + password;
    }
}