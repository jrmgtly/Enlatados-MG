package com.proyectofinal.jrmg.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {
    private String cui;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String direccion;
}
