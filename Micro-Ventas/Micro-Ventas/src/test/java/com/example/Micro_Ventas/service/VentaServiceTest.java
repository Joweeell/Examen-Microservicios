package com.example.Micro_Ventas.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.Micro_Ventas.model.Venta;
import com.example.Micro_Ventas.repository.VentaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class VentaServiceTest {

    @Autowired
    private VentaService ventaService;

    @MockitoBean
    private VentaRepository ventaRepository;

    // 🚀 Simulamos tus Feign Clients para que el test corra en aislamiento puro sin encender los otros microservicios
    @MockitoBean
    private com.example.Micro_Ventas.client.UsuarioClient usuarioClient;

    @MockitoBean
    private com.example.Micro_Ventas.client.ProductoClient productoClient;

    @MockitoBean
    private com.example.Micro_Ventas.client.InventarioClient inventarioClient;

    @MockitoBean
    private com.example.Micro_Ventas.client.EnvioClient envioClient;

    @Test
    public void testListarVentas() {
        Venta venta = new Venta();
        venta.setId(1L);
        venta.setUsuarioId(1L);
        venta.setTotal(45000.0);

        when(ventaRepository.findAll()).thenReturn(List.of(venta));

        // Corregido: Llama a tu método real 'listarVentas' 🚀
        List<Venta> lista = ventaService.listarVentas();

        assertNotNull(lista);
        assertEquals(1, lista.size());
    }

    @Test
    public void testListarPorUsuario() {
        Long usuarioId = 1L;
        Venta venta = new Venta();
        venta.setUsuarioId(usuarioId);

        when(ventaRepository.findByUsuarioId(usuarioId)).thenReturn(List.of(venta));

        // Corregido: Llama a tu método real 'listarPorUsuario' 🚀
        List<Venta> lista = ventaService.listarPorUsuario(usuarioId);

        assertNotNull(lista);
        assertFalse(lista.isEmpty());
    }
}
