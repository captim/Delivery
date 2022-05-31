package com.dumanskyi.delivery.controller;

import com.dumanskyi.delivery.persistence.UserRepository;
import com.dumanskyi.delivery.services.api.UserService;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final String CUSTOMER_CREATED = "Customer successfully created!";

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }
}
