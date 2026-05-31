package com.proyectofinal.jrmg.structures.lista;

import com.proyectofinal.jrmg.model.Caja;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodoCaja {
    private Caja dato;
    private NodoCaja siguiente;

    public NodoCaja(Caja dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
