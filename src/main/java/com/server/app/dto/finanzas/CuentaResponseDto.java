package com.server.app.dto.finanzas;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CuentaResponseDto {
    private Long id;
    private String alias;
    private String moneda;
    private BigDecimal saldoBase;
    private String tipo;
}