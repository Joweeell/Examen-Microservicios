package com.example.Micro_Envios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "envios")
public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ventaId;           // Relación con el Micro_Ventas
    private String direccion;
    private String comuna;
    private String ciudad;
    private String estadoEnvio;     // Ejemplo: "PREPARACION", "EN_CAMINO", "ENTREGADO"
    private String trackingNumber;  // Número de seguimiento para el cliente
    private LocalDateTime fechaDespacho;
}