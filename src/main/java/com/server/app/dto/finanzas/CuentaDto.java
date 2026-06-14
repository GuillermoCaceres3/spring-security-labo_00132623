package com.server.app.dto.finanzas;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CuentaDto {

    @NotBlank(message = "El alias no puede estar vacío")
    private String alias;

    @NotBlank(message = "La moneda no puede estar vacía")
    private String moneda;

    @NotBlank(message = "El tipo no puede estar vacío")
    private String tipo;
}