package app.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelpController {
    @GetMapping("/")
    public String HomeRoute() {
        return "Welcome to the Suming Rest api";
    }
}
