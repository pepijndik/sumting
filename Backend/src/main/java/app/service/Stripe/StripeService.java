package app.service.Stripe;

import app.service.Stripe.Requests.ChargeRequest;
import app.service.Stripe.Requests.CreateSubscriptionRequest;
import com.stripe.Stripe;

import app.server.Stripe.StripeConfig;
import com.stripe.model.*;
import com.stripe.net.Webhook;
import com.stripe.param.WebhookEndpointCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stripe.exception.StripeException;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Stripe wrapper class
 *
 * @Author: Pepijn dik
 * @Since 6-12-2022
 */


@Service
public class StripeService {
    @Autowired
    private StripeConfig stripeConfig;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeConfig.getSecretKey();
    }

    /**
     * Charge a credit card
     *
     * @param chargeRequest
     * @return
     * @throws StripeException
     */
    public Charge charge(ChargeRequest chargeRequest) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", chargeRequest.getAmount());
        chargeParams.put("currency", chargeRequest.getCurrency());
        chargeParams.put("description", chargeRequest.getDescription());
        chargeParams.put("source", chargeRequest.getStripeToken());
        return Charge.create(chargeParams);
    }

    public void Subscripe() {

    }

    public Event getEvent(String payload, String sigHeader) throws StripeException {
        return Webhook.constructEvent(payload, sigHeader, stripeConfig.getWebhookSecret());
    }
    /**
     * Enable webhooks for stipe
     *
     * @throws StripeException
     */
    public void EnableWebhook() throws StripeException {
        Map<String, Object> ExtraParms = new HashMap<>();
        ExtraParms.put("live_mode", stripeConfig.isLive());
        WebhookEndpointCreateParams params =
                WebhookEndpointCreateParams.builder()
                        .setUrl(stripeConfig.getWebhookUrl())
                        .addAllEnabledEvent(Arrays.asList(
                                WebhookEndpointCreateParams.EnabledEvent.CHARGE__FAILED,
                                WebhookEndpointCreateParams.EnabledEvent.CHARGE__SUCCEEDED,
                                WebhookEndpointCreateParams.EnabledEvent.PRODUCT__CREATED,
                                WebhookEndpointCreateParams.EnabledEvent.PRODUCT__UPDATED,
                                WebhookEndpointCreateParams.EnabledEvent.PRODUCT__DELETED,
                                WebhookEndpointCreateParams.EnabledEvent.PLAN__CREATED,
                                WebhookEndpointCreateParams.EnabledEvent.PLAN__UPDATED,
                                WebhookEndpointCreateParams.EnabledEvent.PLAN__DELETED,
                                WebhookEndpointCreateParams.EnabledEvent.CUSTOMER__UPDATED,
                                WebhookEndpointCreateParams.EnabledEvent.CUSTOMER__CREATED))
                        .putAllExtraParam(ExtraParms)
                        .build();
       WebhookEndpoint.create(params);
    }


    public static class Subscription{
        public static com.stripe.model.Subscription create(CreateSubscriptionRequest subscriptionRequest) throws StripeException {
            Map<String, Object> params = new HashMap<>();
            params.put("customer", subscriptionRequest.getCustomer());
            params.put("items", subscriptionRequest.getProducts());
            return com.stripe.model.Subscription.create(params);
        }

        public static com.stripe.model.Subscription cancel(String subscriptionId) throws StripeException {
            return com.stripe.model.Subscription.retrieve(subscriptionId).cancel();
        }

        public static com.stripe.model.Subscription update(String subscriptionId, CreateSubscriptionRequest subscriptionRequest) throws StripeException {
            Map<String, Object> params = new HashMap<>();
            params.put("customer", subscriptionRequest.getCustomer());
            params.put("items", subscriptionRequest.getProducts());
            return com.stripe.model.Subscription.retrieve(subscriptionId).update(params);
        }
    }

}
