package com.example.dutylogbackend.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtResponse implements Serializable {
	private final boolean verified;
	private final String token;
}
