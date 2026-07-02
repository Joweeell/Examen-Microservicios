package com.example.Micro_Ventas.assemblers;

import com.example.Micro_Ventas.controller.VentaControllerV2;
import com.example.Micro_Ventas.model.Venta;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class VentaModelAssembler implements RepresentationModelAssembler<Venta, EntityModel<Venta>> {

    @Override
    public EntityModel<Venta> toModel(Venta venta) {
        return EntityModel.of(venta,
                linkTo(methodOn(VentaControllerV2.class).getVentaById(venta.getId())).withSelfRel(),
                linkTo(methodOn(VentaControllerV2.class).getAllVentas()).withRel("ventas"));
    }
}
