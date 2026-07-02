package com.example.Micro_Inventario.controller;


import com.example.Micro_Inventario.model.Inventario;
import com.example.Micro_Inventario.service.InventarioService;
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

import java.util.List;
/* http://localhost:8082/swagger-ui/index.html#/ */
@RestController     // puerto 8082
@RequestMapping("/api/v1/inventario")
@Tag(name = "Inventario", description = "Operaciones relacionadas con el control de stock y ubicaciones en bodega")

public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping("/listar")
    @Operation(summary = "Obtener todo el inventario", description = "Obtiene una lista completa de los registros de stock y bodegas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventario listado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron registros de inventario")
    })
    public ResponseEntity<List<Inventario>> listar() {
        List<Inventario> lista = inventarioService.listarTodo();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/buscar/{id}")
    @Operation(summary = "Obtener inventario por ID", description = "Busca un registro de inventario por su ID único transaccional")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventario encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Registro de inventario no encontrado")
    })
    public ResponseEntity<Inventario> obtenerPorId(@PathVariable Long id) {
        Inventario inventario = inventarioService.obtenerPorId(id);
        return new ResponseEntity<>(inventario, HttpStatus.OK);
    }

    @GetMapping("/producto/{productoId}")
    @Operation(summary = "Obtener inventario por Producto ID", description = "Busca las existencias asociadas a un ID de producto específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventario del producto encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró inventario para este producto")
    })
    public ResponseEntity<Inventario> obtenerPorProductoId(@PathVariable Long productoId) {
        Inventario inventario = inventarioService.obtenerPorProductoId(productoId);
        return new ResponseEntity<>(inventario, HttpStatus.OK);
    }

    @PutMapping("/sumar/{productoId}/{cantidad}")
    @Operation(summary = "Sumar stock a un producto", description = "Incrementa la cantidad disponible de un producto específico en su bodega")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock incrementado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inventario.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado en el inventario")
    })
    public ResponseEntity<?> sumar(@PathVariable Long productoId, @PathVariable int cantidad){
        Inventario resultado = inventarioService.agregarStock(productoId,cantidad);
        if (resultado != null){
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        }
        return new ResponseEntity<>("Producto no encontrado en inventario", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/restar/{productoId}/{cantidad}")
    @Operation(summary = "Restar stock a un producto", description = "Descuenta unidades de stock tras una venta o movimiento de bodega")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock descontado y actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error: Stock insuficiente o el producto no cuenta con registro inicial")
    })
    public ResponseEntity<String> restar(@PathVariable Long productoId, @PathVariable int cantidad){
        boolean ok = inventarioService.descontarStock(productoId,cantidad);
        if (ok) {
            return new ResponseEntity<>("Stock actualizado correctamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("Error: Stock insuficiente o producto inexistente",HttpStatus.BAD_REQUEST);
    }

}
