package com.dumanskyi.delivery.controller;

import com.dumanskyi.delivery.entities.Role;
import com.dumanskyi.delivery.entities.User;
import com.dumanskyi.delivery.persistence.UserRepository;
import com.dumanskyi.delivery.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {
    final UserRepository userRepository;
    private final String NOT_UNIQUE_EMAIL_MSG = "This email is already used!";
    private final String NOT_UNIQUE_USERNAME_MSG = "This username is already used!";
    private final String CUSTOMER_CREATED = "Customer successfully created!";

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    @ResponseBody
    public List<User> allUsers() {
        return userRepository.findAll();
    }
    @PostMapping("/createCustomer")
    public ResponseEntity<ResponseMessage<?>> createNewCustomer(@RequestBody User user) {
        if (userRepository.findUserByEmailEquals(user.getEmail()) != null) {
            return ResponseUtil.noBodyResponseMessage(NOT_UNIQUE_EMAIL_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (userRepository.findUserByUsernameEquals(user.getUsername()) != null) {
            return ResponseUtil.noBodyResponseMessage(NOT_UNIQUE_USERNAME_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        user.setRole(Role.CUSTOMER);
        userRepository.save(user);
        return ResponseUtil.noBodyResponseMessage(CUSTOMER_CREATED, HttpStatus.CREATED);
    }
}
