package com.WeddingPlanning.Backend.Controller;

import com.WeddingPlanning.Backend.Model.*;
import com.WeddingPlanning.Backend.Service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/")
    public String home() {
        return "submit-review";
    }

    @PostMapping("/submit")
    public String submitReview(@RequestParam String vendorName,
                               @RequestParam String reviewerName,
                               @RequestParam String content,
                               @RequestParam int rating,
                               @RequestParam String type) throws IOException {

        Review review = type.equalsIgnoreCase("verified")
                ? new VerifiedReview(vendorName, reviewerName, content, rating)
                : new GuestReview(vendorName, reviewerName, content, rating);

        reviewService.saveReview(review);
        return "redirect:/view";
    }

    @GetMapping("/view")
    public String viewReviews(Model model) throws IOException {
        model.addAttribute("reviews", reviewService.getAllReviews());
        return "view-reviews";
    }

    @GetMapping("/admin")
    public String adminPanel(Model model) throws IOException {
        model.addAttribute("reviews", reviewService.getAllReviews());
        return "admin-moderation";
    }

    @PostMapping("/delete")
    public String deleteReview(@RequestParam String reviewerName) throws IOException {
        reviewService.deleteReview(reviewerName);
        return "redirect:/admin";
    }
}
