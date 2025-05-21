package com.WeddingPlanning.Backend.Service;

import com.WeddingPlanning.Backend.Model.GuestReview;
import com.WeddingPlanning.Backend.Model.Review;
import com.WeddingPlanning.Backend.Model.VerifiedReview;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ReviewService {

    private static final String FILE_NAME = "static/reviews.txt";
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
        try {
            ClassPathResource resource = new ClassPathResource(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\t");
                if (parts.length == 6) {
                    String id = parts[0];
                    String type = parts[1];
                    String vendorName = parts[2];
                    String reviewerName = parts[3];
                    int rating = Integer.parseInt(parts[4]);
                    String content = parts[5];

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
            reader.close();
        } catch (Exception e) {
            System.err.println("Failed to load reviews: " + e.getMessage());
        }
    }

    private void saveReviewsToFile() {
        try {
            File file = new ClassPathResource(FILE_NAME).getFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (Review r : reviews) {
                writer.write(r.getId() + "\t" +
                        r.getType() + "\t" +
                        r.getVendorName() + "\t" +
                        r.getReviewerName() + "\t" +
                        r.getRating() + "\t" +
                        r.getContent());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Failed to save reviews: " + e.getMessage());
        }
    }
}