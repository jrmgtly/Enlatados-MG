package com.proyectofinal.jrmg.service;

import com.proyectofinal.jrmg.model.Caja;
import com.proyectofinal.jrmg.structures.lista.ListaSimpleCaja;
import com.proyectofinal.jrmg.structures.pila.PilaCaja;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlmacenService {
    private final PilaCaja pilaAlmacen;
    private int contadorCorrelativo;

    public AlmacenService() {
        this.pilaAlmacen = new PilaCaja();
        this.contadorCorrelativo = 1;
    }

    public PilaCaja getPilaAlmacen() {
        return pilaAlmacen;
    }

    public synchronized void agregarCajas(int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            Caja nuevaCaja = Caja.builder()
                    .correlativo(contadorCorrelativo++)
                    .fechaIngreso(LocalDateTime.now())
                    .build();
            pilaAlmacen.push(nuevaCaja);
        }
    }

    public synchronized boolean haySuficientesCajas(int cantidad) {
        return pilaAlmacen.tamanio() >= cantidad;
    }

    public synchronized ListaSimpleCaja extraerCajas(int cantidad) {
        if (!haySuficientesCajas(cantidad)) {
            return null;
        }

        ListaSimpleCaja listaDestino = new ListaSimpleCaja();
        for (int i = 0; i < cantidad; i++) {
            Caja caja = pilaAlmacen.pop();
            if (caja != null) {
                listaDestino.agregar(caja);
            }
        }
        return listaDestino;
    }

    public synchronized int obtenerStock() {
        return pilaAlmacen.tamanio();
    }

    public synchronized List<Caja> obtenerTodas() {
        return pilaAlmacen.obtenerTodas();
    }
}
