package com.example.Micro_Envios.controller;

import com.example.Micro_Envios.assemblers.EnvioModelAssembler;
import com.example.Micro_Envios.model.Envio;
import com.example.Micro_Envios.service.EnvioService;
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
@RequestMapping("/api/v2/envios") // Versión 2 HAL-JSON 🚀
@Tag(name = "Envíos V2", description = "Operaciones de logística y despacho bajo formato hipermedia HATEOAS")
public class EnvioControllerV2 {

    @Autowired
    private EnvioService envioService;

    @Autowired
    private EnvioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los envíos (HATEOAS)", description = "Devuelve el listado completo de órdenes de despacho en formato hipermedia")
    public CollectionModel<EntityModel<Envio>> getAllEnvios() {
        List<Envio> listaPlana = envioService.listarTodos();

        List<EntityModel<Envio>> envios = listaPlana.stream()
                .map(envio -> assembler.toModel(envio))
                .collect(Collectors.toList());

        return CollectionModel.of(envios,
                linkTo(methodOn(EnvioControllerV2.class).getAllEnvios()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener envío por ID (HATEOAS)", description = "Recupera una orden de despacho con sus enlaces dinámicos")
    public EntityModel<Envio> getEnvioById(@PathVariable Long id) {
        Envio envio = envioService.listarTodos().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
        return assembler.toModel(envio);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Registrar un envío (HATEOAS)", description = "Crea una nueva orden de despacho y retorna el recurso interactivo")
    public ResponseEntity<EntityModel<Envio>> createEnvio(@RequestBody Envio envio) {
        Envio nuevoEnvio = envioService.crearEnvio(envio);
        return ResponseEntity
                .created(linkTo(methodOn(EnvioControllerV2.class).getEnvioById(nuevoEnvio.getId())).toUri())
                .body(assembler.toModel(nuevoEnvio));
    }
}
