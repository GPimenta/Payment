package com.payment.payment.model;

import java.util.List;

public class PaymentListDetails {
    private List<PaymentDetails> data;
    private long total;
    private String next;

    public PaymentListDetails(List<PaymentDetails> result, long count, String next) {
        this.data = result;
        this.total = count;
        this.next = next;
    }

    public List<PaymentDetails> getData() {
        return data;
    }

    public void setData(List<PaymentDetails> data) {
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
