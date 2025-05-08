package com.WeddingPlanning.Backend.Model;

public class GuestReview extends Review {
    public GuestReview(String vendorName, String reviewerName, String content, int rating) {
        super(vendorName, reviewerName, content, rating, "Guest");
    }
}
