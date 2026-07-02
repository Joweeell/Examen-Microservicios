package com.example.Micro_Ventas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "micro-inventario", url = "http://localhost:8082")
public interface InventarioClient {

    @PutMapping("/api/v1/inventario/restar/{productoId}/{cantidad}")
    String descontarStock(@PathVariable("productoId") Long productoId,
                          @PathVariable("cantidad") int cantidad);
}