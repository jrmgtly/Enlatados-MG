package com.proyectofinal.jrmg.structures.lista;

import com.proyectofinal.jrmg.model.Caja;
import java.util.ArrayList;
import java.util.List;

public class ListaSimpleCaja {
    private NodoCaja cabeza;
    private int tamanio;

    public ListaSimpleCaja() {
        this.cabeza = null;
        this.tamanio = 0;
    }

    public synchronized void agregar(Caja caja) {
        NodoCaja nuevo = new NodoCaja(caja);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoCaja temp = cabeza;
            while (temp.getSiguiente() != null) {
                temp = temp.getSiguiente();
            }
            temp.setSiguiente(nuevo);
        }
        tamanio++;
    }

    public synchronized int tamanio() {
        return tamanio;
    }

    public synchronized List<Caja> obtenerTodas() {
        List<Caja> cajas = new ArrayList<>();
        NodoCaja temp = cabeza;
        while (temp != null) {
            cajas.add(temp.getDato());
            temp = temp.getSiguiente();
        }
        return cajas;
    }

    public synchronized NodoCaja getCabeza() {
        return cabeza;
    }
}
