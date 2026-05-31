package com.proyectofinal.jrmg.service;

import com.proyectofinal.jrmg.model.Cliente;
import com.proyectofinal.jrmg.structures.avl.ArbolAVL;
import com.proyectofinal.jrmg.util.CsvParser;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {
    private final ArbolAVL arbolClientes;

    public ClienteService() {
        this.arbolClientes = new ArbolAVL();
    }

    public ArbolAVL getArbolClientes() {
        return arbolClientes;
    }

    public synchronized boolean crearCliente(Cliente cliente) {
        if (arbolClientes.buscar(cliente.getCui()) != null) {
            return false; // El cliente ya existe
        }
        arbolClientes.insertar(cliente);
        return true;
    }

    public synchronized Cliente buscarPorCui(String cui) {
        return arbolClientes.buscar(cui);
    }

    public synchronized List<Cliente> obtenerTodos() {
        return arbolClientes.obtenerTodos();
    }

    public synchronized boolean modificarCliente(Cliente cliente) {
        return arbolClientes.modificar(cliente);
    }

    public synchronized boolean eliminarCliente(String cui) {
        if (arbolClientes.buscar(cui) == null) {
            return false;
        }
        arbolClientes.eliminar(cui);
        return true;
    }

    public synchronized int cargarClientesCsv(String contenidoCsv, List<String> errores) {
        List<Cliente> clientesNuevos = CsvParser.parseClientes(contenidoCsv, errores);
        int insertados = 0;
        for (Cliente c : clientesNuevos) {
            if (crearCliente(c)) {
                insertados++;
            } else {
                errores.add("Cliente con CUI/DPI " + c.getCui() + " ya existe.");
            }
        }
        return insertados;
    }
}
