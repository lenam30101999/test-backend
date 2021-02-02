package com.aibles.authenticationservice.repository;

import com.aibles.authenticationservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User save(User user);

    boolean existsUserByEmail(String email);

    Optional<User> findByEmail(String email);

    User findUserByEmail(String email);

    User findById(int id);

    User findUserByUsernameOrPhoneNumberOrEmail(String username, String phoneNumber, String email);

    User findUserByUsername(String username);

    User findUserByPhoneNumber(String phoneNumber);

    User findUserByEmailOrEmail(String emailUpper, String emailLower);
}
