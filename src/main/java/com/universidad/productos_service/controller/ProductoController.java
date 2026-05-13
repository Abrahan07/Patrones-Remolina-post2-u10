package com.universidad.productos_service.controller;

import com.universidad.productos_service.domain.Producto;
import com.universidad.productos_service.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    // Corrección: inyección por constructor en lugar de @Autowired en campo
    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Producto> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.buscar(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}