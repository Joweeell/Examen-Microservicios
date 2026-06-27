package com.example.Micro_Ventas.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.example.Micro_Ventas.model.Venta;
import com.example.Micro_Ventas.service.VentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VentaService ventaService;

    private Venta venta;

    @BeforeEach
    void setUp() {
        venta = new Venta();
        venta.setId(1L);
        venta.setUsuarioId(1L);
        venta.setTotal(49990.0);
        venta.setFechaVenta(LocalDateTime.now());
    }

    // 🧪 Test 1: Listado general de ventas
    @Test
    public void testGetAllVentasV1() throws Exception {
        when(ventaService.listarVentas()).thenReturn(List.of(venta));

        mockMvc.perform(get("/api/v1/ventas/listar"))
                .andExpect(status().isOk());
    }

    // 🧪 Test 2: Buscar el historial de compras de un usuario específico 🚀
    @Test
    public void testGetVentasByUsuarioV1() throws Exception {
        when(ventaService.listarPorUsuario(1L)).thenReturn(List.of(venta));

        // Ajusta si el path de tu controlador cambia (ej: /usuario/1)
        mockMvc.perform(get("/api/v1/ventas/usuario/1"))
                .andExpect(status().isOk());
    }
}
