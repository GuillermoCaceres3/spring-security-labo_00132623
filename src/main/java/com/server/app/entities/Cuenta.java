package com.server.app.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "cuentas")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String alias;

    private String moneda;

    private BigDecimal saldoBase = BigDecimal.ZERO;

    private String tipo; // AHORRO / CORRIENTE

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;
}