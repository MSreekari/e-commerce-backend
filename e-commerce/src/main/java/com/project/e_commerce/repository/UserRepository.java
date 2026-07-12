package com.project.e_commerce.repository;

import com.project.e_commerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Integer id(int id);
}
