package app.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelpController {
    @GetMapping("/help")
    public String getAllOffers() {
        return "Welcome to the Suming Rest api";
    }
}
