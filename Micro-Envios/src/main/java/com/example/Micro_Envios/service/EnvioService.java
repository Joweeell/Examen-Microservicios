package com.example.Micro_Envios.service;

import com.example.Micro_Envios.model.Envio;
import com.example.Micro_Envios.repository.EnvioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnvioService {

    @Autowired
    private EnvioRepository envioRepository;

    public Envio crearEnvio(Envio envio) {
        envio.setEstadoEnvio("EN PREPARACION");

        envio.setTrackingNumber("DESPACHO-" + envio.getVentaId());

        envio.setFechaDespacho(LocalDateTime.now());

        return envioRepository.save(envio);
    }

    public List<Envio> listarTodos() {
        return envioRepository.findAll();
    }

    public Envio actualizarEstado(Long id, String nuevoEstado) {
        return envioRepository.findById(id).map(envio -> {
            envio.setEstadoEnvio(nuevoEstado);
            return envioRepository.save(envio);
        }).orElse(null);
    }
}