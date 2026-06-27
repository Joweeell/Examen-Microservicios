package com.example.Micro_Usuarios.service;

import com.example.Micro_Usuarios.model.Usuario;
import com.example.Micro_Usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    //REGISTRAR USUARIO
    public Usuario registrarUsuario(Usuario usuario){
            // 1. Validar correo duplicado
            if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()){
                throw new RuntimeException("El correo " + usuario.getEmail() + " ya esta en uso.");
            }

            // 2. ASIGNAR ROL POR DEFECTO (Si es nulo)
            if (usuario.getRol() == null) {
                usuario.setRol(com.example.Micro_Usuarios.model.enums.Rol.CLIENTE);
            }

            return usuarioRepository.save(usuario);
    }
    //LISTAR TODOS LOS USUARIOS
    public List<Usuario> obtenerTodos(){return usuarioRepository.findAll();}

    //BUSCAR POR ID
    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    public List<Usuario> buscarPorNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }

    // Actualizar usuario (Lógica para no pisar el ID)
    public Usuario actualizarUsuario(Long id, Usuario datosActualizados) {
        Usuario usuarioExistente = obtenerPorId(id);

        usuarioExistente.setNombre(datosActualizados.getNombre());
        usuarioExistente.setDireccion(datosActualizados.getDireccion());
        usuarioExistente.setTelefono(datosActualizados.getTelefono());
        // El email y password suelen manejarse con métodos aparte por seguridad

        return usuarioRepository.save(usuarioExistente);
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
