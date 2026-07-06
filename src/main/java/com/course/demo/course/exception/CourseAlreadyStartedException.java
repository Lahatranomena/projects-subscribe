package com.course.demo.course.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CourseAlreadyStartedException extends RuntimeException {
    public CourseAlreadyStartedException(String message) {
        super(message);
    }
}