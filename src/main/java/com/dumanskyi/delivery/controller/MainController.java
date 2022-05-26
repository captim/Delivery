package com.dumanskyi.delivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    @GetMapping(path = "/welcome")
    public String welcomePage() {
        return "index";
    }

    @GetMapping(path = "/")
    public ModelAndView indexPage() {
        return new ModelAndView("redirect:/welcome");
    }

    @GetMapping(path = "/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping(path = "/registration")
    public String registrationPage() {
        return "registration";
    }

}
