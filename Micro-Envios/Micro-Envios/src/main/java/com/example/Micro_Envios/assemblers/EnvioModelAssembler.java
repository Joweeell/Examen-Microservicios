package com.example.Micro_Envios.assemblers;

import com.example.Micro_Envios.controller.EnvioControllerV2;
import com.example.Micro_Envios.model.Envio;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EnvioModelAssembler implements RepresentationModelAssembler<Envio, EntityModel<Envio>> {

    @Override
    public EntityModel<Envio> toModel(Envio envio) {
        // Vincula el enlace dinámico al detalle del envío y a la lista general de despachos
        return EntityModel.of(envio,
                linkTo(methodOn(EnvioControllerV2.class).getEnvioById(envio.getId())).withSelfRel(),
                linkTo(methodOn(EnvioControllerV2.class).getAllEnvios()).withRel("envios"));
    }
}
