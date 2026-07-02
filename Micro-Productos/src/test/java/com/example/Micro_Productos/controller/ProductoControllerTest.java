package com.example.Micro_Productos.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.example.Micro_Productos.model.Producto;
import com.example.Micro_Productos.service.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Notebook Gamer");
        producto.setDescripcion("Artículo de alta gama");
        producto.setPrecio(799990.0);
        producto.setStock(50);
        producto.setCategoria("Tecnología");
    }

    @Test
    public void testGetAllProductos() throws Exception {
        when(productoService.obtenerTodos()).thenReturn(List.of(producto));

        mockMvc.perform(get("/api/v2/productos")
                        .accept("application/hal+json"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetProductoById() throws Exception {
        when(productoService.obtenerPorId(1L)).thenReturn(producto);

        mockMvc.perform(get("/api/v2/productos/1")
                        .accept("application/hal+json"))
                .andExpect(status().isOk());
    }
}
