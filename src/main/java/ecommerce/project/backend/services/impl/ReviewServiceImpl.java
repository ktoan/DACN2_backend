package ecommerce.project.backend.services.impl;

import ecommerce.project.backend.dto.ReviewDTO;
import ecommerce.project.backend.entities.Product;
import ecommerce.project.backend.entities.Review;
import ecommerce.project.backend.entities.User;
import ecommerce.project.backend.exceptions.NotAccessException;
import ecommerce.project.backend.exceptions.NotFoundException;
import ecommerce.project.backend.mappers.ReviewMapper;
import ecommerce.project.backend.repositories.ReviewRepository;
import ecommerce.project.backend.requests.ReviewRequest;
import ecommerce.project.backend.services.ProductService;
import ecommerce.project.backend.services.ReviewService;
import ecommerce.project.backend.utils.context.ContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static ecommerce.project.backend.constants.Messaging.REVIEW_NOT_FOUND_ID_MSG;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductService productService;
    private final ContextService contextService;
    private final ReviewMapper reviewMapper;

    @Override
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review findReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new NotFoundException(String.format(REVIEW_NOT_FOUND_ID_MSG, reviewId)));
    }

    @Override
    public ReviewDTO createReview(Long productId, ReviewRequest reviewRequest) {
        Product product = productService.findProductById(productId);
        User user = contextService.loadUserFromContext();
        Review review = new Review();
        review.setComment(reviewRequest.getComment());
        review.setProduct(product);
        review.setValue(reviewRequest.getParentId() == null ? reviewRequest.getValue() : 0);
        review.setUser(user);
        if (reviewRequest.getParentId() != null) {
            Review parent = findReviewById(reviewRequest.getParentId());
            review.setParent(parent);
        }
        review = saveReview(review);
        return reviewMapper.toDTO(review);
    }

    @Override
    public ReviewDTO updateReview(Long reviewId, ReviewRequest reviewRequest) {
        Review review = findReviewById(reviewId);
        if (!Objects.equals(review.getUser().getId(), contextService.loadUserFromContext().getId())) {
            throw new NotAccessException();
        }
        review.setValue(review.getValue());
        review.setComment(review.getComment());
        review = saveReview(review);
        return reviewMapper.toDTO(review);
    }

    @Override
    public void deleteReview(Long reviewId) {
        Review review = findReviewById(reviewId);
        if (!Objects.equals(review.getUser().getId(), contextService.loadUserFromContext().getId())) {
            throw new NotAccessException();
        }
        Product product = review.getProduct();
        product.removeReview(reviewId);
        productService.saveProduct(product);
        Review parent = review.getParent();
        if (parent != null) {
            parent.removeChild(reviewId);
            saveReview(parent);
        }
        reviewRepository.delete(review);
    }
}
