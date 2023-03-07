package com.onurcansever.blog.exception;

import org.springframework.http.HttpStatus;

public class BlogApiException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String message;

    public BlogApiException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
