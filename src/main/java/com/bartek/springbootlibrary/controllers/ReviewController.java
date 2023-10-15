package com.bartek.springbootlibrary.controllers;

import com.bartek.springbootlibrary.requestsModels.ReviewRequest;
import com.bartek.springbootlibrary.services.ReviewService;
import com.bartek.springbootlibrary.utils.ExtractJWT;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/reviews")
@AllArgsConstructor
public class ReviewController {
    private ReviewService reviewService;
    public static final String SUB = "\"sub\"";


    @PostMapping("/secure")
    public void postReview(@RequestHeader(value = "Authorization") String token, @RequestBody ReviewRequest reviewRequest) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, SUB);
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        reviewService.postReview(userEmail, reviewRequest);
    }

    @GetMapping("/secure/user/book")
    public Boolean reviewBookByUser(@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, SUB);
        return reviewService.userReviewListed(userEmail, bookId);
    }
}
