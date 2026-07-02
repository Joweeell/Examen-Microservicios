package com.example.Micro_Inventario.service;

import com.example.Micro_Inventario.model.Inventario;
import com.example.Micro_Inventario.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;


    public Inventario obtenerPorId(Long id) {
        return inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de inventario no encontrado con ID: " + id));
    }

    public Inventario obtenerPorProductoId(Long productoId) {
        return inventarioRepository.findByProductoId(productoId)
                .orElseThrow(() -> new RuntimeException("No existe inventario asociado al producto ID: " + productoId));
    }

    public Inventario agregarStock(Long productoId, int cantidad){
        Optional<Inventario> registro = inventarioRepository.findByProductoId(productoId);

        if (registro.isPresent()){
            Inventario inv = registro.get();
            inv.setCantidadDisponible(inv.getCantidadDisponible() + cantidad);
            inv.setUltimaActualizacion(LocalDateTime.now());
            return inventarioRepository.save(inv);
        }
        return null;
    }
    public boolean descontarStock(Long productoId, int cantidad){
        Optional<Inventario> registro = inventarioRepository.findByProductoId(productoId);

        if (registro.isPresent()){
            Inventario inv = registro.get();
            if (inv.getCantidadDisponible() >= cantidad){
                inv.setCantidadDisponible(inv.getCantidadDisponible() - cantidad);
                inv.setUltimaActualizacion(LocalDateTime.now());
                inventarioRepository.save(inv);
                return true;
            }
        }
        return false;
    }

    public List<Inventario> listarTodo() {
        return inventarioRepository.findAll();
    }
}
