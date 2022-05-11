package com.njbailey.dutylogbackend.controller;

import javax.annotation.Generated;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.njbailey.dutylogbackend.ResourceNotFoundException;
import com.njbailey.dutylogbackend.model.User;
import com.njbailey.dutylogbackend.repository.UserRepository;
import com.njbailey.dutylogbackend.security.JwtTokenUtil;
import com.njbailey.dutylogbackend.security.service.JwtAuthenticationResponse;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Generated("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody User request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(null);
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(request);

        // Reload password post-security so we can generate the token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
    	User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " doesn't exist."));
    	userRepository.delete(user);
    	return ResponseEntity.ok(null);
    }
}
