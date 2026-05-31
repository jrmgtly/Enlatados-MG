package com.proyectofinal.jrmg.model;

import java.time.LocalDateTime;
import com.proyectofinal.jrmg.structures.lista.ListaSimpleCaja;
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
public class Pedido {
    private int numeroPedido;
    private Departamento departamentoOrigen;
    private Departamento departamentoDestino;
    private LocalDateTime fechaHoraInicio;
    private Cliente cliente;
    private Repartidor repartidor;
    private Vehiculo vehiculo;
    private ListaSimpleCaja cajas;
    private int numeroCajas;
    private EstadoPedido estado;
}
