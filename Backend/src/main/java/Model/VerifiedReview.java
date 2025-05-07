package com.WeddingPlanning.Backend.Model;

public class VerifiedReview extends Review {
    public VerifiedReview(String vendorName, String reviewerName, String content, int rating) {
        super(vendorName, reviewerName, content, rating);
    }

    @Override
    public boolean isVerified() {
        return true;
    }
}
