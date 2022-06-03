package com.dumanskyi.delivery.services.impl;

import com.dumanskyi.delivery.entities.db.Role;
import com.dumanskyi.delivery.entities.db.User;
import com.dumanskyi.delivery.persistence.UserRepository;
import com.dumanskyi.delivery.services.api.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Override
    public UserDetails getCurrentUserDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (UserDetails)principal;
    }

    @Override
    public User getCurrentUser() {
        return userRepository.findByUsername(getCurrentUserDetails().getUsername());
    }

}
