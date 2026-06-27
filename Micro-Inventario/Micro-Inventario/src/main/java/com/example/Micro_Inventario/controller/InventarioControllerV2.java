package com.example.Micro_Inventario.controller;

import com.example.Micro_Inventario.assemblers.InventarioModelAssembler;
import com.example.Micro_Inventario.model.Inventario;
import com.example.Micro_Inventario.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/inventario") // Versión 2 con soporte HAL-JSON
@Tag(name = "Inventario V2", description = "Operaciones de inventario bajo formato hipermedia HATEOAS")
public class InventarioControllerV2 {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private InventarioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todo el inventario (HATEOAS)", description = "Devuelve la colección completa de stock con formato hipermedia")
    public CollectionModel<EntityModel<Inventario>> getAllInventario() {
        List<Inventario> listaPlana = inventarioService.listarTodo();

        List<EntityModel<Inventario>> inventarios = listaPlana.stream()
                .map(inventario -> assembler.toModel(inventario))
                .collect(Collectors.toList());

        return CollectionModel.of(inventarios,
                linkTo(methodOn(InventarioControllerV2.class).getAllInventario()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener registro por ID (HATEOAS)", description = "Recupera un registro de inventario con sus enlaces de navegación")
    public EntityModel<Inventario> getInventarioById(@PathVariable Long id) {
        // Asumiendo que agregaste un buscarPorId en tu Service; si no, se puede mapear a tu lógica actual
        Inventario inventario = inventarioService.listarTodo().stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElse(null);
        return assembler.toModel(inventario);
    }

    @GetMapping(value = "/producto/{productoId}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener inventario por Producto ID (HATEOAS)", description = "Recupera las existencias asociadas a un ID de producto con sus enlaces de navegación")
    public EntityModel<Inventario> getInventarioByProductoId(@PathVariable Long productoId) {
        // Llama de forma directa a tu nuevo método real de la capa de servicio 🚀
        Inventario inventario = inventarioService.obtenerPorProductoId(productoId);

        // El assembler se encarga de empaquetar el objeto y pegarle los enlaces automáticamente
        return assembler.toModel(inventario);
    }



}
