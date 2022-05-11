package com.njbailey.dutylogbackend;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String reason) {
        super(reason);
    }

    public ResourceNotFoundException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
