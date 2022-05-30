package com.dumanskyi.delivery.services.impl;

import com.dumanskyi.delivery.entities.Role;
import com.dumanskyi.delivery.entities.User;
import com.dumanskyi.delivery.exceptions.UserException;
import com.dumanskyi.delivery.persistence.UserRepository;
import com.dumanskyi.delivery.services.api.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final String NOT_UNIQUE_EMAIL_USERNAME_MSG = "This email and/or username is already used!";

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void validateAndCreateCustomer(User user) throws UserException {

        if (userRepository.findUserByEmailEqualsOrUsernameEquals(user.getEmail(), user.getUsername()).isPresent()) {
            throw new UserException(NOT_UNIQUE_EMAIL_USERNAME_MSG);
        }
        user.setRole(Role.CUSTOMER);
        userRepository.save(user);
    }
}
