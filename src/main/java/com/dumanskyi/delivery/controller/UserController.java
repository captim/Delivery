package com.dumanskyi.delivery.controller;

import com.dumanskyi.delivery.entities.Role;
import com.dumanskyi.delivery.entities.User;
import com.dumanskyi.delivery.exceptions.UserException;
import com.dumanskyi.delivery.persistence.UserRepository;
import com.dumanskyi.delivery.services.api.UserService;
import com.dumanskyi.delivery.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final String CUSTOMER_CREATED = "Customer successfully created!";

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/users")
    @ResponseBody
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/anonymous/uniqueUsername")
    @ResponseBody
    public ResponseMessage<Boolean> isUsernameUnique(@PathParam("username") String username) {
        User user = userRepository.findUserByUsernameEquals(username);
        if (userRepository.findUserByUsernameEquals(username) == null) {
            return new ResponseMessage<>("Username '" + username + "' is unique", true);
        }
        return new ResponseMessage<>("Username '" + username + "' is already used!", false);
    }

    @GetMapping("/anonymous/uniqueEmail")
    @ResponseBody
    public ResponseMessage<Boolean> isEmailUnique(@PathParam("email") String email) {
        if (userRepository.findUserByEmailEquals(email) == null) {
            return new ResponseMessage<>("Email \"" + email + "\" is unique", true);
        }
        return new ResponseMessage<>("Email \"" + email + "\" is already used!", false);
    }

    @PostMapping("/createCustomer")
    public ResponseEntity<ResponseMessage<?>> createNewCustomer(@RequestBody User user) {
        try {
            userService.validateAndCreateCustomer(user);
        } catch (UserException e) {
            return ResponseUtil.noBodyResponseMessage(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return ResponseUtil.noBodyResponseMessage(CUSTOMER_CREATED, HttpStatus.CREATED);
    }
}
