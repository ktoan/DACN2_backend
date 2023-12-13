package ecommerce.project.backend.payment.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;
import com.stripe.model.Token;
import com.stripe.net.RequestOptions;
import com.stripe.param.CustomerRetrieveParams;
import ecommerce.project.backend.requests.StripeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Override
    public String createCardToken(StripeRequest stripeRequest) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        Map<String, Object> card = new HashMap<>();
        card.put("number", "4242 4242 4242 4242");
        card.put("exp_month", stripeRequest.getExpMonth());
        card.put("exp_year", stripeRequest.getExpYear());
        card.put("cvc", stripeRequest.getCvc());
        Map<String, Object> params = new HashMap<>();
        params.put("card", card);

        Token token = Token.create(params);
        return token.getId();
    }

    @Override
    public String createCustomer(StripeCustomerInformation customerInformation) throws Exception {
        Stripe.apiKey = stripeApiKey;
        if (isExistedUserEmail(customerInformation.getEmail())) {
            throw new Exception("Email stripe customer is already taken!");
        }
        Map<String, Object> customerParams = Map.of("email", customerInformation.getEmail(), "name", customerInformation.getName());
        Customer newCustomer = Customer.create(customerParams);
        return newCustomer.getId();
    }

    private boolean isExistedUserEmail(String email) throws StripeException {
        boolean isExisted;
        Stripe.apiKey = stripeApiKey;
        Map<String, Object> checkParams = Map.of("email", email);
        CustomerCollection customers = Customer.list(checkParams);
        isExisted = !customers.getData().isEmpty();
        return isExisted;
    }
}
