package com.example.dutylogbackend.security.controller;

public class AuthenticationException extends RuntimeException {
	public AuthenticationException(String reason) {
		super(reason);
	}
	
	public AuthenticationException(String reason, Throwable cause) {
		super(reason, cause);
	}
}
