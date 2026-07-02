package com.example.Micro_Envios.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.example.Micro_Envios.model.Envio;
import com.example.Micro_Envios.service.EnvioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EnvioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EnvioService envioService;

    private Envio envio;

    @BeforeEach
    void setUp() {
        envio = new Envio();
        envio.setId(1L);
        envio.setVentaId(10L);
        envio.setDireccion("Av. Concha y Toro 1340");
        envio.setEstadoEnvio("EN PREPARACION");
        envio.setTrackingNumber("DESPACHO-10");
    }

    @Test
    public void testGetAllEnviosV1() throws Exception {
        when(envioService.listarTodos()).thenReturn(List.of(envio));
        mockMvc.perform(get("/api/v1/envios/listar"))
                .andExpect(status().isOk());
    }

    @Test
    public void testActualizarEstadoV1() throws Exception {
        when(envioService.actualizarEstado(eq(1L), any(String.class))).thenReturn(envio);
        mockMvc.perform(patch("/api/v1/envios/actualizar-estado/1")
                        .param("nuevoEstado", "ENTREGADO"))
                .andExpect(status().isOk());
    }
}
