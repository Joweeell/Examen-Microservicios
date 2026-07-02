package com.example.Micro_Inventario.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.Micro_Inventario.model.Inventario;
import com.example.Micro_Inventario.repository.InventarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class InventarioServiceTest {

    @Autowired
    private InventarioService inventarioService;

    @MockitoBean
    private InventarioRepository inventarioRepository;

    @Test
    public void testListarTodo() {
        Inventario inventario = new Inventario();
        inventario.setId(1L);
        inventario.setProductoId(1L);
        inventario.setCantidadDisponible(50);

        when(inventarioRepository.findAll()).thenReturn(List.of(inventario));

        List<Inventario> lista = inventarioService.listarTodo();

        assertNotNull(lista);
        assertEquals(1, lista.size());
    }

    @Test
    public void testObtenerPorId() {
        Long id = 1L;
        Inventario inventario = new Inventario();
        inventario.setId(id);

        when(inventarioRepository.findById(id)).thenReturn(Optional.of(inventario));

        Inventario found = inventarioService.obtenerPorId(id);

        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testObtenerPorProductoId() {
        Long productoId = 10L;
        Inventario inventario = new Inventario();
        inventario.setProductoId(productoId);
        inventario.setCantidadDisponible(30);

        when(inventarioRepository.findByProductoId(productoId)).thenReturn(Optional.of(inventario));

        Inventario found = inventarioService.obtenerPorProductoId(productoId);

        assertNotNull(found);
        assertEquals(productoId, found.getProductoId());
    }

    @Test
    public void testAgregarStockExitoso() {
        Inventario inventario = new Inventario();
        inventario.setProductoId(1L);
        inventario.setCantidadDisponible(10);

        when(inventarioRepository.findByProductoId(1L)).thenReturn(Optional.of(inventario));
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventario);

        Inventario resultado = inventarioService.agregarStock(1L, 5);

        assertNotNull(resultado);
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }

    @Test
    public void testDescontarStockExitoso() {
        Inventario inventario = new Inventario();
        inventario.setProductoId(1L);
        inventario.setCantidadDisponible(20);

        when(inventarioRepository.findByProductoId(1L)).thenReturn(Optional.of(inventario));

        boolean resultado = inventarioService.descontarStock(1L, 5);

        assertTrue(resultado);
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }

}
