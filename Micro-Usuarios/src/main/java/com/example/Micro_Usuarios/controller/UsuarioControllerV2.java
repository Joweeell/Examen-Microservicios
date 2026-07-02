package com.example.Micro_Usuarios.controller;

import com.example.Micro_Usuarios.assemblers.UsuarioModelAssembler;
import com.example.Micro_Usuarios.model.Usuario;
import com.example.Micro_Usuarios.service.UsuarioService;
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
@RequestMapping("/api/v2/usuarios") // Versión 2 con HATEOAS
@Tag(name = "Usuarios V2", description = "Operaciones de usuarios bajo formato hipermedia HAL-JSON")
public class UsuarioControllerV2 {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los usuarios (HATEOAS)", description = "Devuelve la colección completa empaquetada con formato hipermedia HATEOAS")
    public CollectionModel<EntityModel<Usuario>> getAllUsuarios() {
        List<EntityModel<Usuario>> usuarios = usuarioService.obtenerTodos().stream()
                .map(u -> assembler.toModel(u))
                .collect(Collectors.toList());


        return CollectionModel.of(usuarios,
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener usuario por ID (HATEOAS)", description = "Recupera un perfil de usuario interactivo con sus enlaces dinámicos")
    public EntityModel<Usuario> getUsuarioById(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        return assembler.toModel(usuario);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Registrar un usuario (HATEOAS)", description = "Registra un usuario y retorna su recurso interactivo con ubicación de cabecera")
    public ResponseEntity<EntityModel<Usuario>> createUsuario(@RequestBody Usuario usuario) {
        Usuario newUsuario = usuarioService.registrarUsuario(usuario);
        return ResponseEntity
                .created(linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(newUsuario.getId())).toUri())
                .body(assembler.toModel(newUsuario));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar un usuario (HATEOAS)", description = "Modifica los datos de un usuario por ID y devuelve el nuevo recurso interactivo")
    public ResponseEntity<EntityModel<Usuario>> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario updatedUsuario = usuarioService.actualizarUsuario(id, usuario);
        return ResponseEntity
                .ok(assembler.toModel(updatedUsuario));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar un usuario (HATEOAS)", description = "Remueve permanentemente un registro de usuario de la base de datos")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/nombre/{nombre}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener usuarios por nombre (HATEOAS)", description = "Filtra usuarios por coincidencia de nombre exacto con enlaces HATEOAS")
    public CollectionModel<EntityModel<Usuario>> getUsuariosByNombre(@PathVariable String nombre) {
        List<EntityModel<Usuario>> usuarios = usuarioService.buscarPorNombre(nombre).stream()
                .map(u -> assembler.toModel(u))
                .collect(Collectors.toList());

        return CollectionModel.of(usuarios,
                linkTo(methodOn(UsuarioControllerV2.class).getUsuariosByNombre(nombre)).withSelfRel());
    }


    @GetMapping(value = "/rol/{rol}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener usuarios por Rol (HATEOAS)", description = "Filtra la colección de usuarios según su privilegio asignado")
    public CollectionModel<EntityModel<Usuario>> getUsuariosByRol(@PathVariable com.example.Micro_Usuarios.model.enums.Rol rol) {

        List<EntityModel<Usuario>> usuarios = usuarioService.obtenerTodos().stream()
                .filter(u -> u.getRol() != null && u.getRol().equals(rol))
                .map(usuario -> assembler.toModel(usuario))

                .collect(Collectors.toList());

        return CollectionModel.of(usuarios,
                linkTo(methodOn(UsuarioControllerV2.class).getUsuariosByRol(rol)).withSelfRel());
    }
}

