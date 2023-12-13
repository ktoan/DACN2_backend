package ecommerce.project.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {
    private ProductDTO product;
    private Integer quantity;
}
