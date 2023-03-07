package com.onurcansever.blog.payload;

import java.util.Date;

public class ErrorResponse {
    private final Date timestamp;
    private final String message;
    private final String details;

    public ErrorResponse(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
