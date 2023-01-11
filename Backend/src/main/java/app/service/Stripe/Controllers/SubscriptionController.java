package app.service.Stripe.Controllers;

import app.security.JWTokenInfo;
import app.service.Stripe.Requests.CreateSubscriptionRequest;
import app.service.Stripe.StripeService;
import com.stripe.exception.StripeException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * This controller handles the subscriptions
 *
 * @author Pepijn Dik
 */
@Controller
public class SubscriptionController {

    /**
     * Creates a subscription
     *
     * @param subscriptionRequest Subscription request
     * @param tokenInfo           Token info
     * @throws StripeException if the subscription fails`
     */
    @PostMapping("/subscription")
    public void CreateSubscription(CreateSubscriptionRequest subscriptionRequest, @RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo tokenInfo) throws StripeException {
//        if(!tokenInfo.getUser()) {
//            throw new AuthorizationException("only administrators can remove members");
//        }
        StripeService.Subscription.create(subscriptionRequest);
    }

    /**
     * Updates a subscription
     *
     * @param sub     Subscription id
     * @param request Subscription request
     * @throws StripeException if the subscription update fails
     */
    @PutMapping("/subscription/{sub}")
    public void UpdateSubscription(@PathVariable("sub") String sub, CreateSubscriptionRequest request) throws StripeException {
        StripeService.Subscription.update(sub, request);
    }

    /**
     * Cancels a subscription
     *
     * @param sub Subscription id
     * @throws StripeException if the subscription cancel fails
     */
    @GetMapping("/subscription/{sub}/cancel")
    public void CancelSubscription(@PathVariable("sub") String sub) throws StripeException {
        StripeService.Subscription.cancel(sub);
    }
}
