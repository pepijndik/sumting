package app.service.Stripe.Requests;

import lombok.Data;

import java.util.HashMap;

/**
 * This class represents a subscription request
 */
@Data
public class CreateSubscriptionRequest
{
    private String Customer;
    private HashMap<String,Object> products;
}
