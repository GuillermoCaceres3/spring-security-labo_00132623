package com.server.app.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "categorias_financieras")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String tipo; // INGRESO / EGRESO

    @ManyToOne
    @JoinColumn(name = "categoria_padre_id")
    private Categoria categoriaPadre;

    private Boolean autoreferencial = false;
}