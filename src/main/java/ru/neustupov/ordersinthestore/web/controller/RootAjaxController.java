package ru.neustupov.ordersinthestore.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootAjaxController {

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }
}
