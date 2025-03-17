package com.project.back_end.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserDataExpection extends RuntimeException {
    

    private static final long serialVersionUID = 1L;

    public UserDataExpection(Long id) {
        super("User not found" + id);
    }
}
