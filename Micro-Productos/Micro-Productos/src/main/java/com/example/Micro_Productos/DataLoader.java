package com.example.Micro_Productos;

import com.example.Micro_Productos.model.Producto;
import com.example.Micro_Productos.repository.ProductoRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void run(String... args) throws Exception {
        if (productoRepository.count() == 0) {
            Faker faker = new Faker(new Locale("es"));

            for (int i = 0; i < 15; i++) {
                Producto producto = new Producto();

                // Seteos limpios usando tus atributos exactos
                producto.setNombre(faker.commerce().productName());
                producto.setDescripcion("Artículo comercial optimizado. Bodega: " + faker.commerce().department());
                producto.setPrecio(faker.number().randomDouble(2, 5990, 49990));
                producto.setStock(faker.number().numberBetween(15, 120));
                producto.setCategoria(faker.commerce().department());

                productoRepository.save(producto);
            }
            System.out.println("✅ ¡Base de datos de Productos poblada correctamente con Datafaker!");
        }
    }
}
