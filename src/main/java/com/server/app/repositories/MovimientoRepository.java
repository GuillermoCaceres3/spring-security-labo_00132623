package com.server.app.repositories;

import com.server.app.entities.Movimiento;
import com.server.app.entities.Cuenta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    Page<Movimiento> findByCuentaIn(Iterable<Cuenta> cuentas, Pageable pageable);
}