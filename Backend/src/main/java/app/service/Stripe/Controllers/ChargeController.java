package app.service.Stripe.Controllers;

import app.service.Stripe.Requests.ChargeRequest;
import app.service.Stripe.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * This controller handles the charge requests
 *
 * @author Pepijn Dik
 */
@Controller
public class ChargeController {
    @Autowired
    private StripeService paymentsService;

    /**
     * Handles the charge request
     *
     * @param chargeRequest Charge request
     * @param model         Model
     * @return Charge result
     * @throws StripeException if the charge fails
     */
    @PostMapping("/payments/charge")
    public String charge(ChargeRequest chargeRequest, Model model)
        throws StripeException {
        chargeRequest.setDescription("Example charge");
        chargeRequest.setCurrency(ChargeRequest.Currency.EUR);
        Charge charge = paymentsService.charge(chargeRequest);

        model.addAttribute("id", charge.getId());
        model.addAttribute("status", charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction());
        return "result";
    }

    /**
     * Handles errors that occur during the charge request
     *
     * @param model Model
     * @param ex    The exception
     * @return Error page
     */
    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }
}
