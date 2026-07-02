package com.example.Micro_Inventario.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.example.Micro_Inventario.model.Inventario;
import com.example.Micro_Inventario.service.InventarioService;
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
public class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InventarioService inventarioService;

    private Inventario inventario;

    @BeforeEach
    void setUp() {
        inventario = new Inventario();
        inventario.setId(1L);
        inventario.setProductoId(10L);
        inventario.setCantidadDisponible(45);
        inventario.setUbicacionBodega("Pasillo B - Estante 2");
        inventario.setUltimaActualizacion(LocalDateTime.now());
    }

    @Test
    public void testGetAllInventarios() throws Exception {
        when(inventarioService.listarTodo()).thenReturn(List.of(inventario));

        mockMvc.perform(get("/api/v1/inventario/listar"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetInventarioById() throws Exception {
        when(inventarioService.obtenerPorId(1L)).thenReturn(inventario);

        mockMvc.perform(get("/api/v1/inventario/buscar/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetInventarioByProductoId() throws Exception {
        when(inventarioService.obtenerPorProductoId(10L)).thenReturn(inventario);

        mockMvc.perform(get("/api/v1/inventario/producto/10"))
                .andExpect(status().isOk());
    }
}
