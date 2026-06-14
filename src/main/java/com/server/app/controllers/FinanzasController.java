package com.server.app.controllers;

import com.server.app.dto.finanzas.CuentaDto;
import com.server.app.dto.finanzas.CuentaResponseDto;
import com.server.app.dto.finanzas.TransferenciaDto;
import com.server.app.dto.response.Pagination;
import com.server.app.dto.response.PaginationMeta;
import com.server.app.entities.Categoria;
import com.server.app.entities.Cuenta;
import com.server.app.entities.Movimiento;
import com.server.app.entities.User;
import com.server.app.services.FinanzasService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finanzas")
public class FinanzasController {

    private final FinanzasService finanzasService;

    public FinanzasController(FinanzasService finanzasService) {
        this.finanzasService = finanzasService;
    }

    @GetMapping("/cuentas")
    public ResponseEntity<Pagination<CuentaResponseDto>> findCuentas(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Cuenta> p = finanzasService.findCuentas(user, page, size);

        return ResponseEntity.ok(new Pagination<>(
                p.getContent().stream()
                        .map(c -> new CuentaResponseDto(
                                c.getId(),
                                c.getAlias(),
                                c.getMoneda(),
                                c.getSaldoBase(),
                                c.getTipo()
                        ))
                        .toList(),
                new PaginationMeta(
                        p.getNumber(),
                        p.getSize(),
                        p.getTotalPages(),
                        p.getTotalElements()
                )
        ));
    }

    @PostMapping("/cuentas")
    public ResponseEntity<Cuenta> createCuenta(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CuentaDto dto
    ) {
        return ResponseEntity.ok(finanzasService.createCuenta(user, dto));
    }

    @GetMapping("/movimientos")
    public ResponseEntity<Pagination<Movimiento>> findMovimientos(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Movimiento> p = finanzasService.findMovimientos(user, page, size);

        return ResponseEntity.ok(new Pagination<>(
                p.getContent(),
                new PaginationMeta(
                        p.getNumber(),
                        p.getSize(),
                        p.getTotalPages(),
                        p.getTotalElements()
                )
        ));
    }

    @PostMapping("/transferencias")
    public ResponseEntity<Movimiento> transferir(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody TransferenciaDto dto
    ) {
        return ResponseEntity.ok(finanzasService.transferir(user, dto));
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> findCategorias() {
        return ResponseEntity.ok(finanzasService.findCategorias());
    }
}