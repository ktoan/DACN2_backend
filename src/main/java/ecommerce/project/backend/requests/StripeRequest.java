package ecommerce.project.backend.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StripeRequest {
    private String number;
    private Integer expMonth;
    private Integer expYear;
    private String cvc;
}
