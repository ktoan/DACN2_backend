package ecommerce.project.backend.mappers;

import ecommerce.project.backend.dto.ReviewDTO;
import ecommerce.project.backend.entities.Review;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewMapper {
    private final ModelMapper modelMapper;

    public Review toEntity(ReviewDTO reviewDTO) {
        return modelMapper.map(reviewDTO, Review.class);
    }

    public ReviewDTO toDTO(Review review) {
        return modelMapper.map(review, ReviewDTO.class);
    }
}
