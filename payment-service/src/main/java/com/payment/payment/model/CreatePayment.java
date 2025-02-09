package com.payment.payment.model;

import java.util.UUID;

public class CreatePayment {
    private UUID id;
    private String type;
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

    public PaymentAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(PaymentAttributes attributes) {
        this.attributes = attributes;
    }
}
