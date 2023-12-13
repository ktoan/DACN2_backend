package ecommerce.project.backend.requests;

import ecommerce.project.backend.enums.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private Long addressId;
    private String description;
    private PaymentMethod paymentMethod;
    private OrderItemRequest[] items;
    private StripeRequest stripeRequest;
}
