package ecommerce.project.backend.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartRequest {
    @NotNull(message = "Product can't be null!")
    private Long productId;
    @NotNull(message = "Quantity can't be null!")
    private Long quantity;
}
