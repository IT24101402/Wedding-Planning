package com.WeddingPlanning.Backend.Controller;

import com.WeddingPlanning.Backend.Model.GuestReview;
import com.WeddingPlanning.Backend.Model.Review;
import com.WeddingPlanning.Backend.Model.VerifiedReview;
import com.WeddingPlanning.Backend.Service.ReviewService;

import jakarta.servlet.http.HttpSession;
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

    @PostMapping("/delete")
    public String deleteReview(@RequestParam String id, HttpSession session) {
        Boolean isAdmin = (Boolean) session.getAttribute("admin");
        if (isAdmin != null && isAdmin) {
            reviewService.deleteReviewById(id);
        }
        return "redirect:/admin";
    }
}
