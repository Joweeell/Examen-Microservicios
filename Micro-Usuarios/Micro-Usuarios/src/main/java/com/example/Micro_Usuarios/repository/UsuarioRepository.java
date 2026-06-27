package com.example.Micro_Usuarios.repository;

import com.example.Micro_Usuarios.model.Usuario;
import com.example.Micro_Usuarios.model.enums.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByNombre(String nombre);

    List<Usuario> findByRol(Rol rol);
}
