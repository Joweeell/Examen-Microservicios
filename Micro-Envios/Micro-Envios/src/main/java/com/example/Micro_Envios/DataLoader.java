package com.example.Micro_Envios;

import com.example.Micro_Envios.model.Envio;
import com.example.Micro_Envios.repository.EnvioRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Locale;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private EnvioRepository envioRepository;

    @Override
    public void run(String... args) throws Exception {
        if (envioRepository.count() == 0) {
            Faker faker = new Faker(new Locale("es"));

            // Estados posibles de un despacho comercial
            String[] estados = {"PREPARACION", "EN_CAMINO", "ENTREGADO"};

            // Generamos registros asociados a 10 ventas simuladas
            for (long i = 1; i <= 10; i++) {
                Envio envio = new Envio();

                envio.setVentaId(i); // Enlaza con IDs de ventas ficticias del 1 al 10
                envio.setDireccion(faker.address().streetAddress());
                envio.setComuna(faker.address().city()); // Usamos city para rellenar comuna de forma realista
                envio.setCiudad(faker.address().cityName());

                // Asigna un estado aleatorio del arreglo
                envio.setEstadoEnvio(estados[faker.number().numberBetween(0, estados.length)]);

                // Genera un número de seguimiento alfanumérico ficticio (Ej: TRK123456789)
                envio.setTrackingNumber("TRK" + faker.number().digits(9));
                envio.setFechaDespacho(LocalDateTime.now().minusDays(faker.number().numberBetween(1, 5)));

                envioRepository.save(envio);
            }
            System.out.println("✅ ¡Base de datos de Envíos poblada correctamente con Datafaker!");
        }
    }
}
