package com.dumanskyi.delivery.services.impl;

import com.dumanskyi.delivery.entities.Role;
import com.dumanskyi.delivery.entities.User;
import com.dumanskyi.delivery.persistence.UserRepository;
import com.dumanskyi.delivery.services.api.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createCustomer(User user) {
        user.setRole(Role.CUSTOMER);
        userRepository.save(user);
    }
}
