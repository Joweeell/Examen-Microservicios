package com.example.Micro_Usuarios.model;


import com.example.Micro_Usuarios.model.enums.Rol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY) //ID autoincrementable
    private Long id;

    private String nombre;

    @Column(unique = true, nullable = false) //EL email no se puede repetir
    private String email;

    private String password;
    private String direccion;
    private String telefono;

    @Enumerated(EnumType.STRING)
    private Rol rol;
}
