package com.proyectofinal.jrmg.service;

import com.proyectofinal.jrmg.dto.request.PedidoRequest;
import com.proyectofinal.jrmg.model.*;
import com.proyectofinal.jrmg.structures.lista.ListaEnlazadaPedido;
import com.proyectofinal.jrmg.structures.lista.ListaSimpleCaja;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {
    private final ListaEnlazadaPedido listaPedidos;
    private final ClienteService clienteService;
    private final AlmacenService almacenService;
    private final RepartidorService repartidorService;
    private final VehiculoService vehiculoService;
    private int contadorPedidos;

    public PedidoService(ClienteService clienteService,
                         AlmacenService almacenService,
                         RepartidorService repartidorService,
                         VehiculoService vehiculoService) {
        this.listaPedidos = new ListaEnlazadaPedido();
        this.clienteService = clienteService;
        this.almacenService = almacenService;
        this.repartidorService = repartidorService;
        this.vehiculoService = vehiculoService;
        this.contadorPedidos = 1;
    }

    public ListaEnlazadaPedido getListaPedidos() {
        return listaPedidos;
    }

    public synchronized Pedido crearPedido(PedidoRequest request) {
        // 1. Validar que el cliente exista
        Cliente cliente = clienteService.buscarPorCui(request.getCuiCliente());
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente con CUI/DPI " + request.getCuiCliente() + " no está registrado.");
        }

        // 2. Validar que haya suficientes cajas en el almacén
        if (!almacenService.haySuficientesCajas(request.getCantidadCajas())) {
            throw new IllegalArgumentException("No hay suficientes cajas en el almacén. Stock actual: " + almacenService.obtenerStock());
        }

        // 3. Validar que haya repartidores disponibles
        if (repartidorService.obtenerCantidadDisponibles() <= 0) {
            throw new IllegalArgumentException("No hay repartidores disponibles en este momento.");
        }

        // 4. Validar que haya vehículos disponibles
        if (vehiculoService.obtenerCantidadDisponibles() <= 0) {
            throw new IllegalArgumentException("No hay vehículos disponibles en este momento.");
        }

        // Si todas las validaciones pasaron, procedemos a extraer
        
        // 5. Extraer repartidor de la cola
        Repartidor repartidor = repartidorService.desencolarDisponible();
        
        // 6. Extraer vehículo de la cola
        Vehiculo vehiculo = vehiculoService.desencolarDisponible();
        
        // 7 & 8. Extraer N cajas del almacén e insertarlas en la lista simple
        ListaSimpleCaja cajasExtraidas = almacenService.extraerCajas(request.getCantidadCajas());

        // 9. Crear el pedido
        Pedido nuevoPedido = Pedido.builder()
                .numeroPedido(contadorPedidos++)
                .departamentoOrigen(request.getDepartamentoOrigen())
                .departamentoDestino(request.getDepartamentoDestino())
                .fechaHoraInicio(LocalDateTime.now())
                .cliente(cliente)
                .repartidor(repartidor)
                .vehiculo(vehiculo)
                .cajas(cajasExtraidas)
                .numeroCajas(request.getCantidadCajas())
                .estado(EstadoPedido.PENDIENTE)
                .build();

        // 10. Insertar el pedido en la lista enlazada de pedidos
        listaPedidos.insertar(nuevoPedido);

        return nuevoPedido;
    }

    public synchronized Pedido completarPedido(int numeroPedido) {
        // 1. Buscar pedido
        Pedido pedido = listaPedidos.buscarPorNumero(numeroPedido);
        
        // 2. Verificar que exista
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido número " + numeroPedido + " no existe.");
        }

        // 3. Verificar que esté en estado PENDIENTE
        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            throw new IllegalStateException("El pedido número " + numeroPedido + " ya ha sido completado.");
        }

        // 4. Cambiar estado a COMPLETADO
        pedido.setEstado(EstadoPedido.COMPLETADO);

        // 5. Regresar el repartidor asignado a la cola
        repartidorService.encolarDisponible(pedido.getRepartidor());

        // 6. Regresar el vehículo asignado a la cola
        vehiculoService.encolarDisponible(pedido.getVehiculo());

        // 7. No regresar las cajas al almacén (ya están con el cliente)
        
        return pedido;
    }

    public synchronized Pedido buscarPorNumero(int numero) {
        return listaPedidos.buscarPorNumero(numero);
    }

    public synchronized List<Pedido> obtenerTodos() {
        return listaPedidos.obtenerTodos();
    }
}
