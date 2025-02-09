package com.payment.payment.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "payments")
public class PaymentDao {

    @Id
    private UUID id;
    @Column(name="created_at")
    private LocalDateTime createAt;
    private String amount;
    private String currency;
    private String debtorAccountNumber;
    private String creditorAccountNumber;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDebtorAccountNumber() {
        return debtorAccountNumber;
    }

    public void setDebtorAccountNumber(String debtorAccountNumber) {
        this.debtorAccountNumber = debtorAccountNumber;
    }

    public String getCreditorAccountNumber() {
        return creditorAccountNumber;
    }

    public void setCreditorAccountNumber(String creditorAccountNumber) {
        this.creditorAccountNumber = creditorAccountNumber;
    }
}
