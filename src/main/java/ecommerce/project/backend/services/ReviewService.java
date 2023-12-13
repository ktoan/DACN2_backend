package ecommerce.project.backend.services;

import ecommerce.project.backend.dto.ReviewDTO;
import ecommerce.project.backend.entities.Review;
import ecommerce.project.backend.requests.ReviewRequest;

public interface ReviewService {
    Review saveReview(Review review);
    Review findReviewById(Long reviewId);
    ReviewDTO createReview(Long productId, ReviewRequest reviewRequest);
    ReviewDTO updateReview(Long reviewId, ReviewRequest reviewRequest);
    void deleteReview(Long reviewId);
}
