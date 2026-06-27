package com.example.Micro_Ventas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;

@FeignClient(name = "micro-productos", url = "http://localhost:8081")
public interface ProductoClient {

    // Apunta exactamente al endpoint que acabas de crear
    @GetMapping("/api/v1/productos/{id}")
    Map<String, Object> obtenerProductoPorId(@PathVariable("id") Long id);
}