package com.example.Micro_Productos.controller;

import com.example.Micro_Productos.model.Producto;
import com.example.Micro_Productos.service.ProductoService;
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
/* http://localhost:8081/swagger-ui/index.html */
@RestController         //puerto 8081
@RequestMapping("/api/v1/productos")
@Tag(name = "Productos", description = "Operaciones relacionadas con los productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/listar")
    @Operation(summary = "Obtener todos los productos", description = "Obtiene una lista de todos los productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto listados correctamente"),
            @ApiResponse(responseCode = "404", description = "Lista de Productos no encontrada")
    })
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Obtener productos por su Categoria", description = "Obtiene una lista de productos por su Categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto listados correctamente"),
            @ApiResponse(responseCode = "404", description = "Lista de Productos no encontrada")
    })
    public ResponseEntity<List<Producto>> listarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(productoService.buscarPorCategoria(categoria));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por su ID", description = "Obtiene un producto mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto Encontrado por ID correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<Producto> buscarPorId(@PathVariable Long id) {
        Producto producto = productoService.obtenerPorId(id); // O como se llame tu metodo en elservice
        return producto != null ?
                ResponseEntity.ok(producto) :
                ResponseEntity.notFound().build();
    }

    @PostMapping("/registrar")
    @Operation(summary = "Registrar producto", description = "Registra un Producto con sus Datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto registrado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "404", description = "Datos de producto inválidos")
    })
    public ResponseEntity<Producto> registrar(@RequestBody Producto producto) {
        return new ResponseEntity<>(productoService.guardar(producto), HttpStatus.CREATED);
    }
    /*agregar producto por json
    * {
        "nombre": "Cactus San Pedro",
        "descripcion": "Cactus nativo, requiere poco riego",
        "precio": 8500.0,
        "stock": 15,
        "categoria": "Suculentas"
       }*/

    @PutMapping("/actualizar/{id}")
    @Operation(summary = "Actualizar producto", description = "Actualiza un Producto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        Producto actualizado = productoService.actualizar(id, producto);
        return actualizado != null ?
                ResponseEntity.ok(actualizado) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un Producto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
