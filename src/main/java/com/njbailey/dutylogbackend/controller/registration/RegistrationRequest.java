package com.njbailey.dutylogbackend.controller.registration;

import com.njbailey.dutylogbackend.model.security.Authority;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class RegistrationRequest {
    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final List<Authority> authorities;
}
