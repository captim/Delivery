package com.dumanskyi.delivery.persistence;

import com.dumanskyi.delivery.entities.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByEmailEqualsOrUsernameEquals(String email, String username);

    @Query(value = "SELECT count(*) FROM users u WHERE u.email = ?1", nativeQuery = true)
    Optional<Integer> findUserByEmailEquals(String email);

    @Query(value = "SELECT count(*) FROM users u WHERE u.username = ?1", nativeQuery = true)
    Optional<Integer> findUserByUsernameEquals(String username);

    User findByUsername(String username);
}
