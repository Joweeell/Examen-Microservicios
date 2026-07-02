package com.example.Micro_Usuarios.controller;

import com.example.Micro_Usuarios.model.Usuario;
import com.example.Micro_Usuarios.service.UsuarioService;
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
/*  http://localhost:8080/doc/swagger-ui/index.html */
@RestController        //puerto 8080
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con los usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listar")
    @Operation(summary = "Obtener todos los usuarios", description = "Obtiene una lista de todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario listados correctamente"),
            @ApiResponse(responseCode = "404", description = "Lista de Usuario no encontrada")
    })
    public ResponseEntity<List<Usuario>> listar(){
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por su ID", description = "Obtiene un usuario mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario Encontrado por ID correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Obtener usuario por nombre", description = "Obtiene un usuario por su nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<List<Usuario>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(usuarioService.buscarPorNombre(nombre));
    }

    @PostMapping("/registrar")
    @Operation(summary = "Registrar usuario", description = "Registra un Usuario con sus Datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario registrado correctamente",
                 content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario) {
        return new ResponseEntity<>(usuarioService.registrarUsuario(usuario), HttpStatus.CREATED);
    }


    @PutMapping("/actualizar/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @Operation(summary = "Actualizar usuario", description = "Actualiza un Usuario por ID")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, usuario));
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina Usuario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    /*agregar usuario en json
    {
        "direccion": "La Florida, Santiago",
            "email": "jua@gmail.com",
            "nombre": "Oswel Andrade",
            "password": "osito123",
            "telefono": "+56912345678"
    }*/
}
