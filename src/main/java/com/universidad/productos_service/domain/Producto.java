package com.universidad.productos_service.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre; // Bug: sin @Column(nullable=false)
    private Double precio;
    private Integer stock;

    // Code Smell: método con lógica de negocio en entidad JPA
    public String getEstado() {
        if (stock == null) return "DESCONOCIDO"; // Bug potencial
        if (stock == 0) return "AGOTADO";
        if (stock > 0 && stock <= 5) return "BAJO";
        if (stock > 5 && stock <= 20) return "NORMAL";
        if (stock > 20 && stock <= 50) return "ALTO";
        if (stock > 50 && stock <= 100) return "MUY_ALTO";
        if (stock > 100) return "EXCEDENTE";
        return "DESCONOCIDO"; // Code Smell: rama inalcanzable
    }
}