package app.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles all requests for the base url
 */
@RestController
public class HelpController {
    @GetMapping("/")
    public String HomeRoute() {
        return "Welcome to the Sumting Rest api";
    }
}
