package com.saida.register_customer.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDateTime createdAt;

    private String accountId;
    @Enumerated(EnumType.STRING)
    private CurrencyType currency;
    private String accountNumber;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private TransferStatus status;
    @Enumerated(EnumType.STRING)
    private TransferType type;
    private Long refundedTransferId;
    @ManyToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    private Customer customer;

}
