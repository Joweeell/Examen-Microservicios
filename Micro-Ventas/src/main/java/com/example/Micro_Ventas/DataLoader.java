package com.example.Micro_Ventas;

import com.example.Micro_Ventas.model.Venta;
import com.example.Micro_Ventas.repository.VentaRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Locale;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private VentaRepository ventaRepository;

    @Override
    public void run(String... args) throws Exception {
        if (ventaRepository.count() == 0) {
            Faker faker = new Faker(new Locale("es"));

            for (long i = 1; i <= 10; i++) {
                Venta venta = new Venta();

                venta.setUsuarioId((long) faker.number().numberBetween(1, 21)); // IDs de usuarios reales (1 al 20)
                venta.setProductoId((long) faker.number().numberBetween(1, 16)); // IDs de productos reales (1 al 15)

                int cantidadComprada = faker.number().numberBetween(1, 5);
                double precioUnitarioSimulado = faker.number().randomDouble(2, 4990, 29990);

                venta.setCantidad(cantidadComprada);
                venta.setPrecioUnitario(precioUnitarioSimulado);

                venta.setTotal(cantidadComprada * precioUnitarioSimulado);
                venta.setFechaVenta(LocalDateTime.now().minusDays(faker.number().numberBetween(1, 10)));

                ventaRepository.save(venta);
            }
            System.out.println("✅ ¡Base de datos de Ventas poblada correctamente con Datafaker!");
        }
    }
}
