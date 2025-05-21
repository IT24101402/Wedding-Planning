package com.WeddingPlanning.Backend.Model;

public class GuestReview extends com.WeddingPlanning.Backend.Model.Review {
    public GuestReview(String vendorName, String reviewerName, String content, int rating) {
        super(vendorName, reviewerName, content, rating, "Guest");
    }

    @Override
    public String display() {
        return getContent();
    }
}