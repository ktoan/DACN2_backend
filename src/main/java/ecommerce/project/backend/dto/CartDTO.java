package ecommerce.project.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO extends BaseDTO {
    private ProductDTO product;
    private Long quantity;
}
