package fr.xibalba.axiumwebsite.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiDocPage {

    @GetMapping("/api")
    public String login() {
        return "api";
    }
}