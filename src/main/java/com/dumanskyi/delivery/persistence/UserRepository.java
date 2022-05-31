package com.dumanskyi.delivery.persistence;

import com.dumanskyi.delivery.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmailEqualsOrUsernameEquals(String email, String username);
    Optional<User> findUserByEmailEquals(String email);
    Optional<User> findUserByUsernameEquals(String username);
    User findByUsername(String username);
}
