package com.payment.payment.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentDao, UUID>, PagingAndSortingRepository<PaymentDao, UUID> {
}
