package com.example.dutylogbackend.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.dutylogbackend.ResourceNotFoundException;
import com.example.dutylogbackend.UserAlreadyExistsException;
import com.example.dutylogbackend.model.JwtResponse;
import com.example.dutylogbackend.model.User;
import com.example.dutylogbackend.repository.UserRepository;
import com.example.dutylogbackend.util.Util;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/{id}")
	public User getUserById(@PathVariable Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find user with ID=" + id));
	}
}
