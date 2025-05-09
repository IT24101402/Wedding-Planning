package com.WeddingPlanning.Backend.Service;

import com.WeddingPlanning.Backend.Model.GuestReview;
import com.WeddingPlanning.Backend.Model.Review;
import com.WeddingPlanning.Backend.Model.VerifiedReview;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ReviewService {

    private static final String FILE_NAME = "reviews.txt";
    private final List<Review> reviews = new ArrayList<>();

    public void addReview(Review review) {
        reviews.add(review);
        saveReviewsToFile();
    }

    public List<Review> getAllReviews() {
        return new ArrayList<>(reviews);
    }

    public void deleteReviewById(String id) {
        Iterator<Review> iterator = reviews.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(id)) {
                iterator.remove();
                break;
            }
        }
        saveReviewsToFile();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadReviewsFromFile() {
        reviews.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length == 6) {
                    String id = parts[0];
                    String vendorName = parts[1];
                    String reviewerName = parts[2];
                    String content = parts[3];
                    int rating = Integer.parseInt(parts[4].trim());
                    String type = parts[5];

                    Review review;
                    if ("Verified".equalsIgnoreCase(type)) {
                        review = new VerifiedReview(vendorName, reviewerName, content, rating);
                    } else {
                        review = new GuestReview(vendorName, reviewerName, content, rating);
                    }


                    java.lang.reflect.Field idField = Review.class.getDeclaredField("id");
                    idField.setAccessible(true);
                    idField.set(review, id);

                    reviews.add(review);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to load reviews: " + e.getMessage());
        }
    }

    private void saveReviewsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Review r : reviews) {

                writer.write(r.getId() + "\t\t" +
                                r.getType() + "\t" +
                                r.getVendorName() + "\t" +
                                r.getReviewerName() + "\t" +
                                r.getRating()  + "/5\t" +
                                r.getContent()
                );
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Failed to save reviews: " + e.getMessage());
        }
    }

}
