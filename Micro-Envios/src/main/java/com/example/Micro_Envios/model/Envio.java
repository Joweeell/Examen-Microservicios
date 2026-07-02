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

    private Long ventaId;
    private String direccion;
    private String comuna;
    private String ciudad;
    private String estadoEnvio;
    private String trackingNumber;
    private LocalDateTime fechaDespacho;
}