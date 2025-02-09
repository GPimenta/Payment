package com.payment.payment.model;

public class PaymentAttributes {
    private String amount;
    private String currency;
    private AccountDetails creditor;
    private AccountDetails debtor;

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

    public AccountDetails getCreditor() {
        return creditor;
    }

    public void setCreditor(AccountDetails creditor) {
        this.creditor = creditor;
    }

    public AccountDetails getDebtor() {
        return debtor;
    }

    public void setDebtor(AccountDetails debtor) {
        this.debtor = debtor;
    }
}
