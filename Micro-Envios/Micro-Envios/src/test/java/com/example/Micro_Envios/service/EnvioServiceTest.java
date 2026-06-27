package com.example.Micro_Envios.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.Micro_Envios.model.Envio;
import com.example.Micro_Envios.repository.EnvioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class EnvioServiceTest {

    @Autowired
    private EnvioService envioService;

    @MockitoBean
    private EnvioRepository envioRepository;

    @Test
    public void testListarTodos() {
        Envio envio = new Envio();
        envio.setId(1L);
        envio.setVentaId(10L);
        envio.setDireccion("Av. Concha y Toro 1340");
        envio.setTrackingNumber("DESPACHO-10");

        // Simula la llamada real al repositorio
        when(envioRepository.findAll()).thenReturn(List.of(envio));

        // Llama a tu método real 🚀
        List<Envio> lista = envioService.listarTodos();

        assertNotNull(lista);
        assertEquals(1, lista.size());
    }

    @Test
    public void testCrearEnvio() {
        Envio envio = new Envio();
        envio.setVentaId(5L);
        envio.setDireccion("Diagonal Paraguay 250");

        when(envioRepository.save(any(Envio.class))).thenReturn(envio);

        // Llama a tu método real 🚀
        Envio guardado = envioService.crearEnvio(envio);

        assertNotNull(guardado);
        assertEquals("EN PREPARACION", guardado.getEstadoEnvio());
    }

    @Test
    public void testActualizarEstadoExitoso() {
        Envio envio = new Envio();
        envio.setId(1L);
        envio.setEstadoEnvio("EN PREPARACION");

        when(envioRepository.findById(1L)).thenReturn(Optional.of(envio));
        when(envioRepository.save(any(Envio.class))).thenReturn(envio);

        // Llama a tu método real 🚀
        Envio actualizado = envioService.actualizarEstado(1L, "ENTREGADO");

        assertNotNull(actualizado);
        assertEquals("ENTREGADO", actualizado.getEstadoEnvio());
    }
}
