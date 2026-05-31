package com.proyectofinal.jrmg.service;

import com.proyectofinal.jrmg.model.Vehiculo;
import com.proyectofinal.jrmg.structures.cola.ColaVehiculo;
import com.proyectofinal.jrmg.util.CsvParser;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VehiculoService {
    private final ColaVehiculo colaDisponibles;

    public VehiculoService() {
        this.colaDisponibles = new ColaVehiculo();
    }

    public ColaVehiculo getColaDisponibles() {
        return colaDisponibles;
    }

    public synchronized boolean crearVehiculo(Vehiculo vehiculo) {
        if (colaDisponibles.buscarPorPlaca(vehiculo.getPlaca()) != null) {
            return false; // Vehiculo ya existe
        }
        colaDisponibles.encolar(vehiculo);
        return true;
    }

    public synchronized Vehiculo buscarPorPlaca(String placa) {
        return colaDisponibles.buscarPorPlaca(placa);
    }

    public synchronized boolean modificarVehiculo(Vehiculo vehiculo) {
        return colaDisponibles.modificar(vehiculo);
    }

    public synchronized boolean eliminarVehiculo(String placa) {
        return colaDisponibles.eliminarPorPlaca(placa);
    }

    public synchronized Vehiculo desencolarDisponible() {
        return colaDisponibles.desencolar();
    }

    public synchronized void encolarDisponible(Vehiculo vehiculo) {
        colaDisponibles.encolar(vehiculo);
    }

    public synchronized int obtenerCantidadDisponibles() {
        return colaDisponibles.tamanio();
    }

    public synchronized List<Vehiculo> obtenerTodos() {
        return colaDisponibles.obtenerTodos();
    }

    public synchronized int cargarVehiculosCsv(String contenidoCsv, List<String> errores) {
        List<Vehiculo> nuevos = CsvParser.parseVehiculos(contenidoCsv, errores);
        int insertados = 0;
        for (Vehiculo v : nuevos) {
            if (crearVehiculo(v)) {
                insertados++;
            } else {
                errores.add("Vehículo con placa " + v.getPlaca() + " ya existe.");
            }
        }
        return insertados;
    }
}
