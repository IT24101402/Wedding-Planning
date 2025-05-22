package com.WeddingPlanning.Backend.Model;

public class Client extends User  {

    private String weddingDate;

    // Constructor, getters, and setters


    public Client( String username, String email, String password, String phone) {
        super( username, email, password, phone);

    }

    public String getWeddingDate() {
        return weddingDate;
    }

    public void setWeddingDate(String weddingDate) {
        this.weddingDate = weddingDate;
    }
}



