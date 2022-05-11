package com.example.dutylogbackend.security;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtAuthenticationRequest implements Serializable {
	private String username;
	private String password;
}
