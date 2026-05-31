package com.proyectofinal.jrmg.dto.request;

import com.proyectofinal.jrmg.model.Departamento;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequest {
    private String cuiCliente;
    private Departamento departamentoOrigen;
    private Departamento departamentoDestino;
    private int cantidadCajas;
}
