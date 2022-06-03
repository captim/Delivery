package com.dumanskyi.delivery.controller;

import com.dumanskyi.delivery.entities.db.User;
import com.dumanskyi.delivery.entities.novaposhta.responses.KeyValue;
import com.dumanskyi.delivery.services.api.NPService;
import com.dumanskyi.delivery.services.api.UserService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class MainController {
    private final UserService userService;
    private final NPService NPService;

    public MainController(UserService userService, NPService NPService) {
        this.userService = userService;
        this.NPService = NPService;
    }

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
    public String registrationPage(@ModelAttribute("user") User user) {
        return "registration";
    }

    @PostMapping(path = "/registration")
    public String RegisterNewUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.createCustomer(user);
        return "redirect:/login?customerUserCreated=true";
    }
    @GetMapping(path = "/post", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String post() {
        return NPService.getStandardWarehouseTypeId();
    }
    @GetMapping(path = "/cities", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<KeyValue> cities(@RequestParam(required = false) String name, @RequestParam int amount) {
        return NPService.getCitiesIdByName(name, amount);
    }
    @GetMapping(path = "/warehouses", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<KeyValue> warehouses(@RequestParam String city, int number) {
        return NPService.getWarehouseByCityIdAndNumber(city, number);
    }
}
