package com.example.Micro_Ventas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ventas")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;    // Viene de Micro_Usuarios
    private Long productoId;   // Viene de Micro_Productos
    private Integer cantidad;
    private Double precioUnitario; // precio del momemto de la venta
    private Double total;
    private LocalDateTime fechaVenta;
}