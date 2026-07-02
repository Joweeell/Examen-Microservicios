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
            String[] estados = {"PREPARACION", "EN_CAMINO", "ENTREGADO"};
            for (long i = 1; i <= 10; i++) {
                Envio envio = new Envio();

                envio.setVentaId(i);
                envio.setDireccion(faker.address().streetAddress());
                envio.setComuna(faker.address().city());
                envio.setCiudad(faker.address().cityName());

                envio.setEstadoEnvio(estados[faker.number().numberBetween(0, estados.length)]);

                envio.setTrackingNumber("TRK" + faker.number().digits(9));
                envio.setFechaDespacho(LocalDateTime.now().minusDays(faker.number().numberBetween(1, 5)));

                envioRepository.save(envio);
            }
            System.out.println("✅ ¡Base de datos de Envíos poblada correctamente con Datafaker!");
        }
    }
}
