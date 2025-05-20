package com.WeddingPlanning.Backend.Model;

public class VerifiedReview extends com.WeddingPlanning.Backend.Model.Review {
    public VerifiedReview(String vendorName, String reviewerName, String content, int rating) {
        super(vendorName, reviewerName, content, rating, "Verified");
    }

    @Override
    public String display() {
        return "Verified_User:  "  + getContent();
    }
}
