package com.payment.payment.service;

import com.payment.payment.model.*;
import com.payment.payment.repository.PaymentDao;
import com.payment.payment.repository.PaymentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService implements IPaymentService{
    private PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<PaymentDetails> getPayment(UUID id) {
        return repository.findById(id).map(PaymentService::fromPaymentDao);
//        return Optional.ofNullable(dao)
    }

    @Override
    public PaymentDetails createPayment(CreatePayment payment) {
        PaymentDao dao = toPaymentDao(payment);
        PaymentDao savedDao = repository.saveAndFlush(dao);
        return fromPaymentDao(savedDao);
    }

    @Override
    public PaymentListDetails listPayments(Pageable pageable) {
        Page<PaymentDetails> page = repository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
//                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "createdAt"))
                        pageable.getSort()
                )
        ).map(PaymentService::fromPaymentDao);

        String next = page.hasNext() ? "/v1/payments?page=" + (page.getNumber() + 1) + "&size=" + page.getSize() : null;

        return new PaymentListDetails(page.getContent(), page.getTotalElements(), next);
    }



    private static PaymentDetails fromPaymentDao(PaymentDao dao) {
        AccountDetails creditor = new AccountDetails();
        creditor.setAccountNumber(dao.getCreditorAccountNumber());

        AccountDetails debtor = new AccountDetails();
        debtor.setAccountNumber(dao.getDebtorAccountNumber());

        PaymentAttributes paymentAttributes = new PaymentAttributes();
        paymentAttributes.setAmount(dao.getAmount());
        paymentAttributes.setCurrency(dao.getCurrency());
        paymentAttributes.setCreditor(creditor);
        paymentAttributes.setDebtor(debtor);

        PaymentDetails result = new PaymentDetails();
        result.setId(dao.getId());
        result.setCreatedAt(dao.getCreateAt());
        result.setType("payments");
        result.setAttributes(paymentAttributes);

        return result;
    }

    private static PaymentDao toPaymentDao(CreatePayment payment) {
        PaymentDao dao = new PaymentDao();

        dao.setId(payment.getId());
        dao.setCreateAt(LocalDateTime.now(Clock.systemUTC()));
        dao.setAmount(payment.getAttributes().getAmount());
        dao.setCurrency(payment.getAttributes().getCurrency());
        dao.setDebtorAccountNumber(payment.getAttributes().getDebtor().getAccountNumber());
        dao.setCreditorAccountNumber(payment.getAttributes().getCreditor().getAccountNumber());

        return dao;
    }
}
