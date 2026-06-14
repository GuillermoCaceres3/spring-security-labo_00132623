package com.server.app.repositories;

import com.server.app.entities.Cuenta;
import com.server.app.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    List<Cuenta> findByUsuario(User usuario);
    Page<Cuenta> findByUsuarioId(Integer usuarioId, Pageable pageable);
    List<Cuenta> findByUsuarioId(Integer usuarioId);
}