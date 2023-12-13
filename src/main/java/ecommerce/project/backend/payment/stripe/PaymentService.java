package ecommerce.project.backend.payment.stripe;

import com.stripe.exception.StripeException;
import ecommerce.project.backend.requests.StripeRequest;

public interface PaymentService {
    String createCardToken(StripeRequest stripeRequest) throws StripeException;
    String createCustomer(StripeCustomerInformation customerInformation) throws Exception;
}
