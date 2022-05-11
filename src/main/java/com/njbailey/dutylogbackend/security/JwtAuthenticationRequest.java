package com.njbailey.dutylogbackend.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtAuthenticationRequest implements Serializable {
    private String username;
    private String password;
}
