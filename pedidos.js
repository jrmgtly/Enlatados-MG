package com.proyectofinal.jrmg.structures.lista;

import com.proyectofinal.jrmg.model.Pedido;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodoPedido {
    private Pedido dato;
    private NodoPedido siguiente;

    public NodoPedido(Pedido dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
