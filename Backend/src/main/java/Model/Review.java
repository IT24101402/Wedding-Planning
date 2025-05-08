package com.WeddingPlanning.Backend.Model;

public class Review {
    private String vendorName;
    private String reviewerName;
    private String content;
    private int rating;
    private String type; // verified or guest

    public Review(String vendorName, String reviewerName, String content, int rating, String type) {
        this.vendorName = vendorName;
        this.reviewerName = reviewerName;
        this.content = content;
        this.rating = rating;
        this.type = type;
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
}
