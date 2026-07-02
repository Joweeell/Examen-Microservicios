package com.example.Micro_Envios.controller;

import com.example.Micro_Envios.model.Envio;
import com.example.Micro_Envios.service.EnvioService;
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
/* http://localhost:8083/swagger-ui/index.html#/ */
@RestController     //puerto 8083
@RequestMapping("/api/v1/envios")
@Tag(name = "Envíos", description = "Operaciones relacionadas con la logística, creación de despachos y seguimiento de estados")

public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @PostMapping("/guardar")
    @Operation(summary = "Registrar un nuevo envío", description = "Crea una orden de despacho inicial para un pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Envío registrado con éxito",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Envio.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos para el despacho")
    })
    public ResponseEntity<Envio> guardar(@RequestBody Envio envio) {
        Envio nuevoEnvio = envioService.crearEnvio(envio);
        return new ResponseEntity<>(nuevoEnvio, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    @Operation(summary = "Obtener todos los envíos", description = "Devuelve una lista completa con todas las órdenes de despacho registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envíos listados correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron registros de envíos")
    })
    public ResponseEntity<List<Envio>> listar() {
        return new ResponseEntity<>(envioService.listarTodos(), HttpStatus.OK);
    }

    @PatchMapping("/actualizar-estado/{id}")
    @Operation(summary = "Actualizar estado de un envío", description = "Modifica parcialmente el estado actual (ej: EN_CAMINO, ENTREGADO) de un despacho usando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado del envío actualizado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Envio.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró ningún envío asociado al ID proporcionado")
    })
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestParam String nuevoEstado) {
        Envio actualizado = envioService.actualizarEstado(id, nuevoEstado);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return new ResponseEntity<>("No se encontró el envío con ID: " + id, HttpStatus.NOT_FOUND);
    }
}