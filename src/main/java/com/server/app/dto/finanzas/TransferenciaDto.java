package com.server.app.dto.finanzas;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferenciaDto {

    @NotNull(message = "La cuenta origen es requerida")
    private Long cuentaOrigenId;

    @NotNull(message = "La cuenta destino es requerida")
    private Long cuentaDestinoId;

    @NotNull(message = "El monto es requerido")
    private BigDecimal monto;

    private String descripcion;
}