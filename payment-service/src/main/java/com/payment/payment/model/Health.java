package com.payment.payment.model;

import java.time.Clock;
import java.time.LocalDateTime;

public class Health {
    private final LocalDateTime timestamp;
    private final String status;

    public Health(String status) {
        this.timestamp = LocalDateTime.now(Clock.systemUTC());
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }
}
