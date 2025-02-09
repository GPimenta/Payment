package com.payment.payment.service;

import com.payment.payment.model.CreatePayment;
import com.payment.payment.model.PaymentDetails;
import com.payment.payment.model.PaymentListDetails;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IPaymentService {

    Optional<PaymentDetails> getPayment(UUID id);

    PaymentDetails createPayment(CreatePayment payment);

    PaymentListDetails listPayments(Pageable pageable);
}
