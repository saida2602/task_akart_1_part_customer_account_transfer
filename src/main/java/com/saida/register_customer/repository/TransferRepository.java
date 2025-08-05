package com.saida.register_customer.repository;

import com.saida.register_customer.domain.Customer;
import com.saida.register_customer.domain.Transfer;
import com.saida.register_customer.domain.TransferStatus;
import com.saida.register_customer.domain.TransferType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer,Long> {

    @Query("SELECT t FROM Transfer t WHERE t.type =:transferType and t.status =:status and " +
            "t.accountNumber=:accountNumber and t.amount=:amount and t.customer=:customer ORDER BY t.createdAt DESC")
    List<Transfer> findByAccountNumber(@Param("amount") BigDecimal amount,
                                       @Param("accountNumber")String accountNumber,
                                       @Param("transferType") TransferType transferType,
                                       @Param("status") TransferStatus status,
                                       @Param("customer") Customer customer);
}
