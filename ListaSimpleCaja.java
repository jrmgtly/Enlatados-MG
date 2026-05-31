package com.proyectofinal.jrmg.service;

import com.proyectofinal.jrmg.model.Repartidor;
import com.proyectofinal.jrmg.structures.cola.ColaRepartidor;
import com.proyectofinal.jrmg.util.CsvParser;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RepartidorService {
    private final ColaRepartidor colaDisponibles;

    public RepartidorService() {
        this.colaDisponibles = new ColaRepartidor();
    }

    public ColaRepartidor getColaDisponibles() {
        return colaDisponibles;
    }

    public synchronized boolean crearRepartidor(Repartidor repartidor) {
        if (colaDisponibles.buscarPorCui(repartidor.getCui()) != null) {
            return false; // Repartidor ya existe
        }
        colaDisponibles.encolar(repartidor);
        return true;
    }

    public synchronized Repartidor buscarPorCui(String cui) {
        return colaDisponibles.buscarPorCui(cui);
    }

    public synchronized boolean modificarRepartidor(Repartidor repartidor) {
        return colaDisponibles.modificar(repartidor);
    }

    public synchronized boolean eliminarRepartidor(String cui) {
        return colaDisponibles.eliminarPorCui(cui);
    }

    public synchronized Repartidor desencolarDisponible() {
        return colaDisponibles.desencolar();
    }

    public synchronized void encolarDisponible(Repartidor repartidor) {
        colaDisponibles.encolar(repartidor);
    }

    public synchronized int obtenerCantidadDisponibles() {
        return colaDisponibles.tamanio();
    }

    public synchronized List<Repartidor> obtenerTodos() {
        return colaDisponibles.obtenerTodos();
    }

    public synchronized int cargarRepartidoresCsv(String contenidoCsv, List<String> errores) {
        List<Repartidor> nuevos = CsvParser.parseRepartidores(contenidoCsv, errores);
        int insertados = 0;
        for (Repartidor r : nuevos) {
            if (crearRepartidor(r)) {
                insertados++;
            } else {
                errores.add("Repartidor con CUI/DPI " + r.getCui() + " ya existe.");
            }
        }
        return insertados;
    }
}
