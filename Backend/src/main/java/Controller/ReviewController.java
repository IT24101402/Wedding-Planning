package com.WeddingPlanning.Backend.Controller;

import com.WeddingPlanning.Backend.Model.*;
import com.WeddingPlanning.Backend.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/")
    public String showSubmitForm() {
        return "submit-review";
    }

    @PostMapping("/submit")
    public String submitReview(@RequestParam String vendorName,
                               @RequestParam String reviewerName,
                               @RequestParam String content,
                               @RequestParam int rating,
                               @RequestParam String type) {
        com.WeddingPlanning.Backend.Model.Review review;
        if (type.equalsIgnoreCase("verified")) {
            review = new com.WeddingPlanning.Backend.Model.Review(vendorName, reviewerName, content, rating, type);
        } else {
            review = new com.WeddingPlanning.Backend.Model.GuestReview(vendorName, reviewerName, content, rating);
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
    public String adminPanel(Model model) {
        model.addAttribute("reviews", reviewService.getAllReviews());
        return "admin-moderation";
    }

    @PostMapping("/delete")
    public String deleteReview(@RequestParam String id) {
        reviewService.deleteReviewById(id);
        return "redirect:/admin";
    }
}
