package com.example.Micro_Ventas.controller;

import com.example.Micro_Ventas.model.Venta;
import com.example.Micro_Ventas.service.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
/* http://localhost:8084/swagger-ui/index.html#/  */
@RestController     //puerto 8084
@RequestMapping("/api/v1/ventas")
@Tag(name = "Ventas", description = "Operaciones orientadas a procesar transacciones comerciales e integrar los flujos del sistema")

public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping("/guardar")
    @Operation(summary = "Registrar una nueva venta", description = "Procesa una transacción integrando los datos del cliente, productos y stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Venta procesada y guardada con éxito",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Venta.class))),
            @ApiResponse(responseCode = "400", description = "Error en la transacción debido a datos inválidos")
    })
    public ResponseEntity<Venta> guardar(@RequestBody Venta venta) {
        return new ResponseEntity<>(ventaService.crearVenta(venta), HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    @Operation(summary = "Obtener el historial de ventas", description = "Devuelve una lista completa de todas las transacciones realizadas en la plataforma")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial de ventas obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron registros de ventas")
    })
    public ResponseEntity<List<Venta>> listar() {
        return new ResponseEntity<>(ventaService.listarVentas(), HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Obtener compras de un usuario", description = "Filtra e identifica el historial de transacciones comerciales de un cliente mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial del cliente recuperado exitosamente")
    })
    public ResponseEntity<List<Venta>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(ventaService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/rango-fechas")
    @Operation(summary = "Filtrar historial por rango de fechas", description = "Devuelve el listado de transacciones ocurridas entre un período de tiempo determinado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte temporal generado con éxito")
    })
    public ResponseEntity<List<Venta>> listarPorFechas(
            @RequestParam @io.swagger.v3.oas.annotations.Parameter(description = "Fecha de inicio (Ejemplo: 2026-01-01T00:00:00)") String inicio,
            @RequestParam @io.swagger.v3.oas.annotations.Parameter(description = "Fecha de término (Ejemplo: 2026-12-31T23:59:59)") String fin) {

        LocalDateTime fechaInicio = LocalDateTime.parse(inicio);
        LocalDateTime fechaFin = LocalDateTime.parse(fin);
        return ResponseEntity.ok(ventaService.listarPorRangoFechas(fechaInicio, fechaFin));
    }
}