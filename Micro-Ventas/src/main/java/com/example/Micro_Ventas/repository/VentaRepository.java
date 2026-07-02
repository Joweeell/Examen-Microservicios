package com.example.Micro_Ventas.repository;

import com.example.Micro_Ventas.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByUsuarioId(Long usuarioId);
    List<Venta> findByFechaVentaBetween(LocalDateTime inicio, LocalDateTime fin);
}
