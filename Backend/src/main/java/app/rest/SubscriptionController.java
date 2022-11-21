package app.rest;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Subscription;
import app.exceptions.AuthorizationException;
import app.security.JWTokenInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SubscriptionController {
    @Value("${stripe.key}")
    private String StripeKey = null;

    public SubscriptionController() {
        Stripe.apiKey = StripeKey;
    }

    @PostMapping("/subscription")
    public void CreateSubscription(String Customer, List<Object> items,@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo tokenInfo) throws StripeException {
//        if(!tokenInfo.getUser()) {
//            throw new AuthorizationException("only administrators can remove members");
//        }


        items = new ArrayList<>();
        Map<String, Object> item1 = new HashMap<>();
        item1.put("price", "price_1Ls3482eZvKYlo2CxRn40GXd");
        items.add(item1);
        Map<String, Object> params = new HashMap<>();
        params.put("customer", Customer);
        params.put("items", items);
        Subscription subscription = Subscription.create(params);
    }
    @PutMapping("/subscription/{subscriptionId}")
    public void UpdateSubscription(@PathVariable String sub) throws StripeException {
        Subscription subscription =
                Subscription.retrieve(
                        "sub_1LrskJ2eZvKYlo2CYB4MQwLh"
                );

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("order_id", "6735");
        Map<String, Object> params = new HashMap<>();
        params.put("metadata", metadata);

        Subscription updatedSubscription =
                subscription.update(params);
    }
    @GetMapping("/subscription/{subscription}cancel")
    public void CancelSubscription(@PathVariable String sub) throws StripeException {
        Subscription subscription = Subscription.retrieve(sub);
        Subscription deletedSubscription = subscription.cancel();
    }
}
