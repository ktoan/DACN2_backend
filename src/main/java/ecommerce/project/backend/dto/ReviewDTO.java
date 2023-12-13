package ecommerce.project.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ReviewDTO extends BaseDTO {
    private Double value;
    private String comment;
    private UserDTO user;
    private Set<ReviewDTO> childList;
}
