package com.example.Micro_Ventas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

// Apunta al puerto 8084 donde corre tu Micro-Envios
@FeignClient(name = "micro-envios" )// url = "http://localhost:8083")
public interface EnvioClient {

    // Ruta completa mapeada al EnvioController de tu otro microservicio
    @PostMapping("/api/v1/envios/guardar")
    Object registrarEnvio(@RequestBody Map<String, Object> datosEnvio);
}