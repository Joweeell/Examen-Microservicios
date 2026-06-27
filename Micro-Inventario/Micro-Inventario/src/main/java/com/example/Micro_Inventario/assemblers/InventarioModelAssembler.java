package com.example.Micro_Inventario.assemblers;

import com.example.Micro_Inventario.controller.InventarioControllerV2;
import com.example.Micro_Inventario.model.Inventario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class InventarioModelAssembler implements RepresentationModelAssembler<Inventario, EntityModel<Inventario>> {

    @Override
    public EntityModel<Inventario> toModel(Inventario inventario) {
        // Enlaza el recurso individual por su ID y el de retorno al listado completo
        return EntityModel.of(inventario,
                linkTo(methodOn(InventarioControllerV2.class).getInventarioById(inventario.getId())).withSelfRel(),
                linkTo(methodOn(InventarioControllerV2.class).getAllInventario()).withRel("inventarios"));
    }
}
