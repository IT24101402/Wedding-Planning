package com.WeddingPlanning.Backend.Model;

public class Photographer extends Vendor {
    public Photographer(Long id, String name, double price, String contact) {
        super(id, name, "Photographer", price, contact);
    }

    // Polymorphic display
    public String getSpecialFeature() {
        return "Captures memories in high-res!";
    }
}
