package com.WeddingPlanning.Backend.Service;

import com.WeddingPlanning.Backend.Model.Review;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ReviewService {
    private final List<Review> reviews = new ArrayList<>();

    public void addReview(Review review) {
        reviews.add(review);
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
    }
}
