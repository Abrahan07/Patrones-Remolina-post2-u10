package com.universidad.productos_service.service;

import com.universidad.productos_service.domain.Producto;
import com.universidad.productos_service.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    ProductoRepository productoRepository;

    @InjectMocks
    ProductoService service;

    @Test
    void buscar_productoNoExiste_lanzaNoSuchElementException() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.buscar(99L));
    }

    @Test
    void buscar_productoExiste_retornaProducto() {
        Producto p = new Producto();
        p.setNombre("Laptop");
        p.setPrecio(3500000.0);
        p.setStock(10);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(p));

        Producto resultado = service.buscar(1L);
        assertThat(resultado.getNombre()).isEqualTo("Laptop");
    }

    @Test
    void procesarProducto_nombreVacio_lanzaIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> service.procesarProducto("  ", 1000.0, 5));
        verify(productoRepository, never()).save(any());
    }

    @Test
    void procesarProducto_precioNegativo_lanzaIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> service.procesarProducto("Laptop", -100.0, 5));
        verify(productoRepository, never()).save(any());
    }

    @Test
    void procesarProducto_datosValidos_guardaYRetorna() {
        Producto p = new Producto();
        p.setNombre("Laptop");
        p.setPrecio(1500.0);
        p.setStock(10);

        when(productoRepository.save(any())).thenReturn(p);

        Producto resultado = service.procesarProducto("Laptop", 1500.0, 10);
        assertThat(resultado.getNombre()).isEqualTo("Laptop");
        verify(productoRepository).save(any());
    }
}