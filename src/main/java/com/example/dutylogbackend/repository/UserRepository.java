package com.example.dutylogbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dutylogbackend.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String name);
}
