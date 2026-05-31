package com.proyectofinal.jrmg.structures.lista;

import com.proyectofinal.jrmg.model.Pedido;
import java.util.ArrayList;
import java.util.List;

public class ListaEnlazadaPedido {
    private NodoPedido cabeza;
    private int tamanio;

    public ListaEnlazadaPedido() {
        this.cabeza = null;
        this.tamanio = 0;
    }

    public synchronized void insertar(Pedido pedido) {
        NodoPedido nuevo = new NodoPedido(pedido);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoPedido temp = cabeza;
            while (temp.getSiguiente() != null) {
                temp = temp.getSiguiente();
            }
            temp.setSiguiente(nuevo);
        }
        tamanio++;
    }

    public synchronized Pedido buscarPorNumero(int numero) {
        NodoPedido temp = cabeza;
        while (temp != null) {
            if (temp.getDato().getNumeroPedido() == numero) {
                return temp.getDato();
            }
            temp = temp.getSiguiente();
        }
        return null;
    }

    public synchronized int tamanio() {
        return tamanio;
    }

    public synchronized List<Pedido> obtenerTodos() {
        List<Pedido> pedidos = new ArrayList<>();
        NodoPedido temp = cabeza;
        while (temp != null) {
            pedidos.add(temp.getDato());
            temp = temp.getSiguiente();
        }
        return pedidos;
    }

    public synchronized NodoPedido getCabeza() {
        return cabeza;
    }
}
