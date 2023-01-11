package app.service.Stripe.Controllers;

import app.service.Stripe.StripeService;
import com.stripe.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;


@Controller
public class WebhookHandler {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/stripe/webhooks")
    public void handleWebhook(HttpServletRequest request, HttpServletResponse response) {
        try {
            String payload = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            if (!StringUtils.hasText(payload)) {
                String sigHeader = request.getHeader("Stripe-Signature");
                Event event = stripeService.getEvent(payload, sigHeader);
                // Deserialize the nested object inside the event
                EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
                StripeObject stripeObject = null;
                if (dataObjectDeserializer.getObject().isPresent()) {
                    stripeObject = dataObjectDeserializer.getObject().get();
                } else {
                    // Deserialization failed, probably due to an API version mismatch.
                }
                switch (event.getType()) {
                    case "payment_intent.succeeded":
                        PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
                        // Then define and call a method to handle the successful payment intent.
                        // handlePaymentIntentSucceeded(paymentIntent);
                        break;
                    case "payment_method.attached":
                        PaymentMethod paymentMethod = (PaymentMethod) stripeObject;
                        // Then define and call a method to handle the successful attachment of a PaymentMethod.
                        // handlePaymentMethodAttached(paymentMethod);
                        break;
                    case "charge.refunded":
                        Charge charge = (Charge) stripeObject;
                        // Call your service here with your Charge object.
                    default:
                        System.out.println("Unhandled event type: " + event.getType());
                }


                response.setStatus(200);
            }
        } catch (Exception e) {
            response.setStatus(500);
        }
    }
}
