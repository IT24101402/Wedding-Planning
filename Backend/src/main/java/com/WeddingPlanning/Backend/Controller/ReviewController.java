package com.WeddingPlanning.Backend.Controller;

import com.WeddingPlanning.Backend.Model.GuestReview;
import com.WeddingPlanning.Backend.Model.Review;
import com.WeddingPlanning.Backend.Model.VerifiedReview;
import com.WeddingPlanning.Backend.Service.ReviewService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/review-form")
    public String showSubmitForm() {
        return "submit-review";
    }

    @PostMapping("/submit")
    public String submitReview(@RequestParam String vendorName,
                               @RequestParam String reviewerName,
                               @RequestParam String content,
                               @RequestParam int rating,
                               @RequestParam String type) {
        Review review;
        if (type.equalsIgnoreCase("verified")) {
            review = new VerifiedReview(vendorName, reviewerName, content, rating);
        } else {
            review = new GuestReview(vendorName, reviewerName, content, rating);
        }
        reviewService.addReview(review);
        return "redirect:/view";
    }

    @GetMapping("/view")
    public String viewReviews(Model model) {
        model.addAttribute("reviews", reviewService.getAllReviews());
        return "view-reviews";
    }

    @GetMapping("/admin")
    public String adminPanel(HttpSession session, Model model) {
        Boolean isAdmin = (Boolean) session.getAttribute("admin");
        if (isAdmin != null && isAdmin) {
            model.addAttribute("reviews", reviewService.getAllReviews());
            return "admin-moderation";
        } else {
            return "redirect:/admin-login";
        }
    }

    @GetMapping("/reviews")
    public String showReviews(Model model) {
        List<Review> reviews = new ArrayList<>();

        try {
            ClassPathResource resource = new ClassPathResource("static/reviews.txt");
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
            e.printStackTrace();
        }

        model.addAttribute("reviews", reviews);
        return "all-reviews";
    }

    @PostMapping("/delete")
    public String deleteReview(@RequestParam String id, HttpSession session) {
        Boolean isAdmin = (Boolean) session.getAttribute("admin");
        if (isAdmin != null && isAdmin) {
            reviewService.deleteReviewById(id);
        }
        return "redirect:/admin";
    }
}