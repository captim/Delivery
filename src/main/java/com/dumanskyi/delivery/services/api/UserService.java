package com.dumanskyi.delivery.services.api;

import com.dumanskyi.delivery.entities.User;
import com.dumanskyi.delivery.exceptions.UserException;

public interface UserService {
    void validateAndCreateCustomer(User user) throws UserException;
}
