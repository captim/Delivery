package com.dumanskyi.delivery.controller;

import com.dumanskyi.delivery.entities.User;
import com.dumanskyi.delivery.persistence.UserRepository;
import com.dumanskyi.delivery.services.api.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("admin")
public class AdminController {
    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(path = "/registration")
    public String registrationPage(@ModelAttribute("user") User user) {
        return "admin_registration";
    }

    @PostMapping(path = "/registration")
    public String RegisterNewUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin_registration";
        }
        userRepository.save(user);
        return "redirect:/admin/registration?customerUserCreated=true";
    }
}
