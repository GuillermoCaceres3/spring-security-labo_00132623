package com.server.app.services;

import com.server.app.dto.finanzas.CuentaDto;
import com.server.app.dto.finanzas.TransferenciaDto;
import com.server.app.entities.Categoria;
import com.server.app.entities.Cuenta;
import com.server.app.entities.Movimiento;
import com.server.app.entities.User;
import com.server.app.exceptions.NotFoundException;
import com.server.app.exceptions.ConfictException;
import com.server.app.repositories.CategoriaRepository;
import com.server.app.repositories.CuentaRepository;
import com.server.app.repositories.MovimientoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class FinanzasService {

    private final CuentaRepository cuentaRepository;
    private final CategoriaRepository categoriaRepository;
    private final MovimientoRepository movimientoRepository;

    @Transactional
    public Page<Cuenta> findCuentas(User user, int page, int size) {
        return cuentaRepository.findByUsuarioId(user.getId(), PageRequest.of(page, size));
    }

    @Transactional
    public Cuenta createCuenta(User user, CuentaDto dto) {
        Cuenta cuenta = new Cuenta();
        cuenta.setAlias(dto.getAlias());
        cuenta.setMoneda(dto.getMoneda());
        cuenta.setTipo(dto.getTipo());
        cuenta.setSaldoBase(BigDecimal.ZERO);
        cuenta.setUsuario(user);

        return cuentaRepository.save(cuenta);
    }

    @Transactional
    public Page<Movimiento> findMovimientos(User user, int page, int size) {
        List<Cuenta> cuentas = cuentaRepository.findByUsuarioId(user.getId());
        return movimientoRepository.findByCuentaIn(cuentas, PageRequest.of(page, size));
    }

    @Transactional
    public Movimiento transferir(User user, TransferenciaDto dto) {
        Cuenta origen = cuentaRepository.findById(dto.getCuentaOrigenId())
                .orElseThrow(() -> new NotFoundException("Cuenta origen no encontrada"));

        Cuenta destino = cuentaRepository.findById(dto.getCuentaDestinoId())
                .orElseThrow(() -> new NotFoundException("Cuenta destino no encontrada"));

        if (origen.getUsuario().getId() != user.getId() || destino.getUsuario().getId() != user.getId()) {
            throw new ConfictException("No puedes transferir usando cuentas de otro usuario");
        }

        if (origen.getSaldoBase().compareTo(dto.getMonto()) < 0) {
            throw new ConfictException("Fondos insuficientes");
        }

        origen.setSaldoBase(origen.getSaldoBase().subtract(dto.getMonto()));
        destino.setSaldoBase(destino.getSaldoBase().add(dto.getMonto()));

        cuentaRepository.save(origen);
        cuentaRepository.save(destino);

        Movimiento movimiento = new Movimiento();
        movimiento.setMonto(dto.getMonto());
        movimiento.setMonedaOriginal(origen.getMoneda());
        movimiento.setTasaCambio(BigDecimal.ONE);
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setDescripcion(dto.getDescripcion() != null ? dto.getDescripcion() : "Transferencia entre cuentas");
        movimiento.setCuenta(origen);

        return movimientoRepository.save(movimiento);
    }

    @Transactional
    public List<Categoria> findCategorias() {
        return categoriaRepository.findAll();
    }
}