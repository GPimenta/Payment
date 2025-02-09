package com.payment.payment.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountDetails {
    @JsonProperty("account_number")
    private String accountNumber;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
