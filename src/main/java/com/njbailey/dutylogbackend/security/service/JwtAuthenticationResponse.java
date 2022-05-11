package com.njbailey.dutylogbackend.security.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
@Getter
public class JwtAuthenticationResponse implements Serializable {
    private final String token;
}
