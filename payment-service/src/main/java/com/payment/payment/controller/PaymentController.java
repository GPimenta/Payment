package com.payment.payment.controller;

import com.payment.payment.model.CreatePayment;
import com.payment.payment.model.PaymentDetails;
import com.payment.payment.model.PaymentListDetails;
import com.payment.payment.service.IPaymentService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/payments")
public class PaymentController {

    private IPaymentService service;

    public PaymentController(IPaymentService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPayment(@PathVariable UUID id) {
        try {
            Optional<PaymentDetails> payment = service.getPayment(id);
            if (payment.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(payment.get());
            }
        }catch(Exception e) {
            // loggar o erro!
            return ResponseEntity.
                    internalServerError().
                    build();
        }
    }

    @GetMapping
    public  ResponseEntity<?> findAll(
            @PageableDefault(
                page = 0,
                size = 10,
                sort = "createAt",
                direction = Sort.Direction.ASC) Pageable pageable) {
        PaymentListDetails paymentListDetails = service.listPayments(pageable);

        return ResponseEntity.ok(paymentListDetails);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postPayment(@RequestBody CreatePayment newPayment, UriComponentsBuilder ucb) {
        service.createPayment(newPayment);
        Optional<PaymentDetails> payment = service.getPayment(newPayment.getId());

        if (payment.isEmpty()) {
            return ResponseEntity.internalServerError().build();
        } else {
            URI uri = ucb.path("v1/payments/{id}")
                    .buildAndExpand(payment.get().getId())
                    .toUri();
            return ResponseEntity.created(uri).build();
        }
    }
}
