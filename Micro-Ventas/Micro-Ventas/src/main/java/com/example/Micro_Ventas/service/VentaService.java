package com.example.Micro_Ventas.service;

import com.example.Micro_Ventas.client.InventarioClient;
import com.example.Micro_Ventas.client.EnvioClient;
import com.example.Micro_Ventas.client.ProductoClient;
import com.example.Micro_Ventas.client.UsuarioClient;
import com.example.Micro_Ventas.model.Venta;
import com.example.Micro_Ventas.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VentaService {

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private ProductoClient productoClient;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private InventarioClient inventarioClient;

    @Autowired
    private EnvioClient envioClient;

    public Venta crearVenta(Venta venta) {
        // 1. VALIDACIÓN DE USUARIO (Puerto 8080)
        usuarioClient.obtenerUsuarioPorId(venta.getUsuarioId());
        // 2. OBTENER PRECIO REAL DESDE MICRO_PRODUCTOS (Puerto 8081)
        Map<String, Object> productoJson = productoClient.obtenerProductoPorId(venta.getProductoId());
        if (productoJson == null || !productoJson.containsKey("precio") || productoJson.get("precio") == null) {
            throw new RuntimeException("El producto no existe o no cuenta con un precio configurado.");
        }
        // Convertimos el precio del mapa a Double de forma limpia
        String precioString = productoJson.get("precio").toString();
        venta.setPrecioUnitario(Double.parseDouble(precioString));

        // 3. LLAMADA AL MICROSERVICIO DE INVENTARIO (Resta stock - Puerto 8082)
        inventarioClient.descontarStock(venta.getProductoId(), venta.getCantidad());

        // 4. Cálculo de totales (Ahora con el precio real asignado)
        venta.setTotal(venta.getCantidad() * venta.getPrecioUnitario());
        venta.setFechaVenta(LocalDateTime.now());

        // 5. Guardamos la transacción en nuestra base de datos de Ventas (Puerto 8083)
        Venta ventaGuardada = ventaRepository.save(venta);

        // 6. LLAMADA AL MICROSERVICIO DE ENVÍOS (Creación automática del despacho - Puerto 8085)
        Map<String, Object> datosEnvio = new HashMap<>();
        datosEnvio.put("ventaId", ventaGuardada.getId());
        datosEnvio.put("direccion", "Av. Concha y Toro 123");
        datosEnvio.put("comuna", "Puente Alto");
        datosEnvio.put("ciudad", "Santiago");
        envioClient.registrarEnvio(datosEnvio);
        return ventaGuardada;
    }
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    public List<Venta> listarPorUsuario(Long usuarioId) {
        return ventaRepository.findByUsuarioId(usuarioId);
    }

    public List<Venta> listarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin){
        return ventaRepository.findByFechaVentaBetween(inicio,fin);
    }
}