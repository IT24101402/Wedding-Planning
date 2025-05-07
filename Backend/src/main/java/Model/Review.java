package com.WeddingPlanning.Backend.Model;

import java.io.Serializable;

public abstract class Review implements Serializable {
    protected String vendorName;
    protected String reviewerName;
    protected String content;
    protected int rating;

    public Review(String vendorName, String reviewerName, String content, int rating) {
        this.vendorName = vendorName;
        this.reviewerName = reviewerName;
        this.content = content;
        this.rating = rating;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public abstract boolean isVerified();

    public String getVendorName() { return vendorName; }
    public String getReviewerName() { return reviewerName; }
    public String getContent() { return content; }
    public int getRating() { return rating; }
}
