package com.proyectofinal.jrmg.structures.pila;

import com.proyectofinal.jrmg.model.Caja;
import java.util.ArrayList;
import java.util.List;

public class PilaCaja {
    private NodoPilaCaja tope;
    private int tamanio;

    public PilaCaja() {
        this.tope = null;
        this.tamanio = 0;
    }

    public synchronized void push(Caja caja) {
        NodoPilaCaja nuevo = new NodoPilaCaja(caja);
        nuevo.setSiguiente(tope);
        tope = nuevo;
        tamanio++;
    }

    public synchronized Caja pop() {
        if (estaVacia()) {
            return null;
        }
        Caja caja = tope.getDato();
        tope = tope.getSiguiente();
        tamanio--;
        return caja;
    }

    public synchronized Caja peek() {
        if (estaVacia()) {
            return null;
        }
        return tope.getDato();
    }

    public synchronized boolean estaVacia() {
        return tope == null;
    }

    public synchronized int tamanio() {
        return tamanio;
    }

    public synchronized List<Caja> obtenerTodas() {
        List<Caja> cajas = new ArrayList<>();
        NodoPilaCaja temp = tope;
        while (temp != null) {
            cajas.add(temp.getDato());
            temp = temp.getSiguiente();
        }
        return cajas;
    }

    public synchronized NodoPilaCaja getTope() {
        return tope;
    }
}
