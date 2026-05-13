package com.universidad.productos_service.repository;

import com.universidad.productos_service.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}