package com.WeddingPlanning.Backend.Model;

import java.util.UUID;

public class Review {
    private String id;
    private String vendorName;
    private String reviewerName;
    private String content;
    private int rating;
    private String type;

    public Review(String vendorName, String reviewerName, String content, int rating, String type) {
        this.id = UUID.randomUUID().toString();
        this.vendorName = vendorName;
        this.reviewerName = reviewerName;
        this.content = content;
        this.rating = rating;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public String getContent() {
        return content;
    }

    public int getRating() {
        return rating;
    }

    public String getType() {
        return type;
    }

    public String display() {
        return reviewerName + " (" + type + "): " + content;
    }
}
