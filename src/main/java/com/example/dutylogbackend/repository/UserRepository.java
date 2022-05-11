package com.example.dutylogbackend.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dutylogbackend.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByName(String name);
}
