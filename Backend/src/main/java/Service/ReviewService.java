package com.WeddingPlanning.Backend.Service;

import com.WeddingPlanning.Backend.Model.*;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class ReviewService {

    private final String filePath = "reviews.txt";

    public void saveReview(Review review) throws IOException {
        try (FileWriter fw = new FileWriter(filePath, true)) {
            fw.write((review instanceof VerifiedReview ? "verified" : "guest") + "," +
                    review.getVendorName() + "," +
                    review.getReviewerName() + "," +
                    review.getContent().replace(",", " ") + "," +
                    review.getRating() + "\n");
        }
    }

    public List<Review> getAllReviews() throws IOException {
        List<Review> reviews = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return reviews;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 5);
                if (parts.length == 5) {
                    String type = parts[0];
                    String vendorName = parts[1];
                    String reviewerName = parts[2];
                    String content = parts[3];
                    int rating = Integer.parseInt(parts[4]);

                    Review r = type.equals("verified")
                            ? new VerifiedReview(vendorName, reviewerName, content, rating)
                            : new GuestReview(vendorName, reviewerName, content, rating);
                    reviews.add(r);
                }
            }
        }
        return reviews;
    }

    public void deleteReview(String reviewerName) throws IOException {
        List<Review> reviews = getAllReviews();
        reviews.removeIf(r -> r.getReviewerName().equalsIgnoreCase(reviewerName));

        try (FileWriter fw = new FileWriter(filePath, false)) {
            for (Review review : reviews) {
                fw.write((review instanceof VerifiedReview ? "verified" : "guest") + "," +
                        review.getVendorName() + "," +
                        review.getReviewerName() + "," +
                        review.getContent().replace(",", " ") + "," +
                        review.getRating() + "\n");
            }
        }
    }
}
