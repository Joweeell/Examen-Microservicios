package com.example.Micro_Ventas.controller;

import com.example.Micro_Ventas.assemblers.VentaModelAssembler;
import com.example.Micro_Ventas.model.Venta;
import com.example.Micro_Ventas.service.VentaService;
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
@RequestMapping("/api/v2/ventas") // Versión 2 HAL-JSON 🚀
@Tag(name = "Ventas V2", description = "Operaciones comerciales y transaccionales bajo formato hipermedia HATEOAS")
public class VentaControllerV2 {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private VentaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener historial de ventas (HATEOAS)", description = "Devuelve el registro completo de transacciones en formato hipermedia")
    public CollectionModel<EntityModel<Venta>> getAllVentas() {
        List<Venta> listaPlana = ventaService.listarVentas();

        List<EntityModel<Venta>> ventas = listaPlana.stream()
                .map(venta -> assembler.toModel(venta))
                .collect(Collectors.toList());

        return CollectionModel.of(ventas,
                linkTo(methodOn(VentaControllerV2.class).getAllVentas()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener venta por ID (HATEOAS)", description = "Recupera una transacción específica con sus enlaces de navegación")
    public EntityModel<Venta> getVentaById(@PathVariable Long id) {
        Venta venta = ventaService.listarVentas().stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElse(null);
        return assembler.toModel(venta);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Registrar una venta (HATEOAS)", description = "Procesa una nueva orden transaccional orquestada y devuelve su recurso interactivo")
    public ResponseEntity<EntityModel<Venta>> createVenta(@RequestBody Venta venta) {
        Venta nuevaVenta = ventaService.crearVenta(venta);
        return ResponseEntity
                .created(linkTo(methodOn(VentaControllerV2.class).getVentaById(nuevaVenta.getId())).toUri())
                .body(assembler.toModel(nuevaVenta));
    }
}
