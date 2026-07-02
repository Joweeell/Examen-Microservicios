package com.example.Micro_Usuarios;

import com.example.Micro_Usuarios.model.Usuario;
import com.example.Micro_Usuarios.model.enums.Rol;
import com.example.Micro_Usuarios.repository.UsuarioRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            Faker faker = new Faker();
            for (int i = 0; i < 20; i++) {
                Usuario usuario = new Usuario();
                usuario.setNombre(faker.name().fullName());
                usuario.setEmail(faker.internet().emailAddress());
                usuario.setPassword("123456");
                usuario.setDireccion(faker.address().fullAddress());
                usuario.setTelefono(faker.phoneNumber().cellPhone());

                usuario.setRol(i % 2 == 0 ? Rol.CLIENTE : Rol.ADMIN);

                usuarioRepository.save(usuario);
            }
            System.out.println("✅ ¡Base de datos de Usuarios poblada correctamente con Datafaker!");
        }
    }
}
