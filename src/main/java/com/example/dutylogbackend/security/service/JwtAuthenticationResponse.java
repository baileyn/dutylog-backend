package com.example.dutylogbackend.security.service;

import java.io.Serializable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class JwtAuthenticationResponse implements Serializable {
	private final String token;
}
