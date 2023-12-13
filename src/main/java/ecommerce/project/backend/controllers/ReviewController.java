package ecommerce.project.backend.controllers;

import ecommerce.project.backend.dto.ReviewDTO;
import ecommerce.project.backend.requests.ReviewRequest;
import ecommerce.project.backend.services.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "*")
@Tag(name = "Review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/create")
    @Operation(summary = "Create review for a product")
    public ResponseEntity<Object> createReview(@RequestParam Long productId, @RequestBody @Valid ReviewRequest reviewRequest) {
        ReviewDTO newReview = reviewService.createReview(productId, reviewRequest);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("newReview", newReview);
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @PutMapping("/update/{reviewId}")
    @Operation(summary = "Update review for a product")
    public ResponseEntity<Object> updateReview(@PathVariable Long reviewId, @RequestBody @Valid ReviewRequest reviewRequest) {
        ReviewDTO updatedReview = reviewService.updateReview(reviewId, reviewRequest);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("updatedReview", updatedReview);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{reviewId}")
    @Operation(summary = "Delete review for a product")
    public ResponseEntity<Object> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("msg", "Delete review successfully!");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
