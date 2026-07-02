package com.example.Micro_Usuarios.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.Micro_Usuarios.model.Usuario;
import com.example.Micro_Usuarios.model.enums.Rol;
import com.example.Micro_Usuarios.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @Test
    public void testObtenerTodos() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan Pérez");
        usuario.setRol(Rol.CLIENTE);

        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> usuarios = usuarioService.obtenerTodos();

        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
    }

    @Test
    public void testObtenerPorId() {
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Juan Pérez");

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        Usuario found = usuarioService.obtenerPorId(id);

        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testRegistrarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan Pérez");
        usuario.setEmail("juan@duocuc.cl");

        when(usuarioRepository.findByEmail("juan@duocuc.cl")).thenReturn(Optional.empty());
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario saved = usuarioService.registrarUsuario(usuario);

        assertNotNull(saved);
        assertEquals("Juan Pérez", saved.getNombre());
        assertEquals(Rol.CLIENTE, saved.getRol()); // Valida que se asigne el rol por defecto
    }

    @Test
    public void testEliminarUsuario() {
        Long id = 1L;

        doNothing().when(usuarioRepository).deleteById(id);

        usuarioService.eliminarUsuario(id);

        verify(usuarioRepository, times(1)).deleteById(id);
    }

    @Test
    public void testActualizarUsuario() {
        Usuario viejo = new Usuario();
        viejo.setId(1L);
        viejo.setNombre("Nombre Viejo");

        Usuario nuevo = new Usuario();
        nuevo.setNombre("Joel Actualizado");

        Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(viejo));
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(viejo);
        Usuario resultado = usuarioService.actualizarUsuario(1L, nuevo);
        Assertions.assertEquals("Joel Actualizado", resultado.getNombre());
    }
}
