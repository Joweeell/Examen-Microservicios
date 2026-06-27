package com.example.Micro_Ventas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "micro-usuarios", url = "http://localhost:8080")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/{id}")
    Object obtenerUsuarioPorId(@PathVariable("id") Long id);
}