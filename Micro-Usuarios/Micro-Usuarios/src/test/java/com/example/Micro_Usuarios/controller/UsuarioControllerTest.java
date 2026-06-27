package com.example.Micro_Usuarios.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.example.Micro_Usuarios.model.Usuario;
import com.example.Micro_Usuarios.model.enums.Rol;
import com.example.Micro_Usuarios.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan Pérez");
        usuario.setEmail("juan.perez@duocuc.cl");
        usuario.setPassword("123456");
        usuario.setDireccion("Av. Concha y Toro 1340");
        usuario.setTelefono("+56912345678");
        usuario.setRol(Rol.CLIENTE);
    }

    @Test
    public void testGetAllUsuarios() throws Exception {
        when(usuarioService.obtenerTodos()).thenReturn(List.of(usuario));

        // Apunta a la ruta real de tu controlador HATEOAS v2 🚀
        mockMvc.perform(get("/api/v2/usuarios")
                        .accept("application/hal+json"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUsuarioById() throws Exception {
        when(usuarioService.obtenerPorId(1L)).thenReturn(usuario);


        mockMvc.perform(get("/api/v2/usuarios/1")
                        .accept("application/hal+json"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateUsuario() throws Exception {
        when(usuarioService.registrarUsuario(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/v2/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testUpdateUsuario() throws Exception {
        when(usuarioService.actualizarUsuario(eq(1L), any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(put("/api/v2/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUsuario() throws Exception {
        doNothing().when(usuarioService).eliminarUsuario(1L);

        mockMvc.perform(delete("/api/v2/usuarios/1"))
                .andExpect(status().is2xxSuccessful());

        verify(usuarioService, times(1)).eliminarUsuario(1L);
    }
}
