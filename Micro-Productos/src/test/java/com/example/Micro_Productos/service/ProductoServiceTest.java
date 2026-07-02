package com.example.Micro_Productos.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.Micro_Productos.model.Producto;
import com.example.Micro_Productos.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductoServiceTest {

    @Autowired
    private ProductoService productoService;

    @MockitoBean
    private ProductoRepository productoRepository;

    @Test
    public void testObtenerTodos() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Libro de Prueba");
        producto.setPrecio(9990.0);

        when(productoRepository.findAll()).thenReturn(List.of(producto));

        List<Producto> productos = productoService.obtenerTodos();

        assertNotNull(productos);
        assertEquals(1, productos.size());
    }

    @Test
    public void testObtenerPorId() {
        Long id = 1L;
        Producto producto = new Producto();
        producto.setId(id);
        producto.setNombre("Libro de Prueba");

        when(productoRepository.findById(id)).thenReturn(Optional.of(producto));

        Producto found = productoService.obtenerPorId(id);

        assertNotNull(found);
        assertEquals(id, found.getId());
    }
}
