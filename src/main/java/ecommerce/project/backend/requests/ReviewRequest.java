package ecommerce.project.backend.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReviewRequest {
    @NotNull(message =  "Review content can't be null!")
    @NotBlank(message =  "Review content can't be blank!")
    private String comment;
    @NotNull(message =  "Review rate can't be null!")
    private Double value;
    private Long parentId;
}
