package com.WeddingPlanning.Backend.Model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Review {
    private final String id;
    private final String vendorName;
    private final String reviewerName;
    private final String content;
    private final int rating;
    private final String type;

    public Review(String vendorName, String reviewerName, String content, int rating, String type) {
        this.id = UUID.randomUUID().toString();
        this.vendorName = vendorName;
        this.reviewerName = reviewerName;
        this.content = content;
        this.rating = rating;
        this.type = type;
    }

    public String display() {
        return content;
    }
}
