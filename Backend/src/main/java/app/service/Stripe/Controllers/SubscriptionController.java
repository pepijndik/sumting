package app.service.Stripe.Controllers;

import app.service.Stripe.Requests.CreateSubscriptionRequest;
import app.service.Stripe.StripeService;
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

    @PostMapping("/subscription")
    public void CreateSubscription(CreateSubscriptionRequest subscriptionRequest, @RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo tokenInfo) throws StripeException {
//        if(!tokenInfo.getUser()) {
//            throw new AuthorizationException("only administrators can remove members");
//        }
        StripeService.Subscription.create(subscriptionRequest);
    }

    @PutMapping("/subscription/{sub}")
    public void UpdateSubscription(@PathVariable("sub") String sub, CreateSubscriptionRequest request) throws StripeException {
        StripeService.Subscription.update(sub, request);
    }

    @GetMapping("/subscription/{sub}/cancel")
    public void CancelSubscription(@PathVariable("sub") String sub) throws StripeException {
        StripeService.Subscription.cancel(sub);
    }
}
