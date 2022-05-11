package com.njbailey.dutylogbackend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class JwtResponse implements Serializable {
    private final boolean verified;
    private final String token;
}
