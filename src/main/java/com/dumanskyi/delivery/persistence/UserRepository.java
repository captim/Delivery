package com.dumanskyi.delivery.persistence;

import com.dumanskyi.delivery.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmailEqualsOrUsernameEquals(String email, String username);
}
