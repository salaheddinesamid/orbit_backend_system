package com.orbit.orbit_banking.modules.banking.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "banking_services")
@Getter
@Setter
public class BankingService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_type", length = 255)
    @Enumerated(EnumType.STRING)
    private ServiceType type;

    @Column(name = "desc")
    private String desc;

    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "fee")
    private BigDecimal fee;
}
