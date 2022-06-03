package com.dumanskyi.delivery.services.api;

import com.dumanskyi.delivery.entities.db.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    void createCustomer(User user);
    UserDetails getCurrentUserDetails();
    User getCurrentUser();
}
