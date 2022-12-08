package app.service.Stripe.Requests;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class CreateSubscriptionRequest
{
    private String Customer;
    private HashMap<String,Object> products;
}
