package com.payment.payment.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonPropertyOrder(value = {"id", "type", "created_at", "attributes"})
public class PaymentDetails {
    private UUID id;
    private String type;
    private LocalDateTime createdAt;
    private PaymentAttributes attributes;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public PaymentAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(PaymentAttributes attributes) {
        this.attributes = attributes;
    }
}
