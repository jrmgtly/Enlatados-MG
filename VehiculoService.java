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
public class Repartidor {
    private String cui;
    private String nombre;
    private String apellidos;
    private TipoLicencia licencia;
    private String telefono;
}
