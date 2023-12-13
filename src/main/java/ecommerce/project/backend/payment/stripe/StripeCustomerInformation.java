package ecommerce.project.backend.payment.stripe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StripeCustomerInformation {
    private String name;
    private String email;
}
