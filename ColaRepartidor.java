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
public class Vehiculo {
    private String placa;
    private String marca;
    private String modelo;
    private String color;
    private int anio;
    private String tipoTransmision;
}
