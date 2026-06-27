package com.example.Micro_Inventario;

import com.example.Micro_Inventario.model.Inventario;
import com.example.Micro_Inventario.repository.InventarioRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Locale;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Override
    public void run(String... args) throws Exception {
        if (inventarioRepository.count() == 0) {
            Faker faker = new Faker(new Locale("es"));

            // Generamos un registro de inventario para cada uno de los 15 productos
            for (long i = 1; i <= 15; i++) {
                Inventario inventario = new Inventario();

                inventario.setProductoId(i);
                inventario.setCantidadDisponible(faker.number().numberBetween(10, 100));

                // Generación nativa universal de una letra de la A a la Z para evitar errores de directivas 🚀
                char letraPasillo = (char) ('A' + faker.number().numberBetween(0, 26));
                inventario.setUbicacionBodega("Pasillo " + letraPasillo + " - Estante " + faker.number().numberBetween(1, 10));

                inventario.setUltimaActualizacion(LocalDateTime.now());

                inventarioRepository.save(inventario);
            }
            System.out.println("✅ ¡Base de datos de Inventario poblada correctamente con Datafaker!");
        }
    }
}
