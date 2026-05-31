package com.proyectofinal.jrmg.service;

import com.proyectofinal.jrmg.model.*;
import com.proyectofinal.jrmg.structures.lista.*;
import com.proyectofinal.jrmg.structures.pila.*;
import com.proyectofinal.jrmg.structures.cola.*;
import com.proyectofinal.jrmg.structures.avl.*;
import com.proyectofinal.jrmg.util.DotUtils;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class ReporteService {
    private final UsuarioService usuarioService;
    private final AlmacenService almacenService;
    private final ClienteService clienteService;
    private final RepartidorService repartidorService;
    private final VehiculoService vehiculoService;
    private final PedidoService pedidoService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ReporteService(UsuarioService usuarioService,
                          AlmacenService almacenService,
                          ClienteService clienteService,
                          RepartidorService repartidorService,
                          VehiculoService vehiculoService,
                          PedidoService pedidoService) {
        this.usuarioService = usuarioService;
        this.almacenService = almacenService;
        this.clienteService = clienteService;
        this.repartidorService = repartidorService;
        this.vehiculoService = vehiculoService;
        this.pedidoService = pedidoService;
    }

    public synchronized String generarDotUsuarios() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph Usuarios {\n");
        sb.append("    rankdir=LR;\n");
        sb.append("    node [shape=record, style=filled, fillcolor=\"#FFEBE0\", color=\"#D4763C\", fontname=\"Helvetica\"];\n");

        NodoUsuario temp = usuarioService.getListaUsuarios().getCabeza();
        if (temp == null) {
            sb.append("    vacio [label=\"Lista de Usuarios Vacía\", shape=box, color=gray];\n");
        } else {
            int i = 1;
            while (temp != null) {
                Usuario u = temp.getDato();
                String label = String.format("{ID: %d | %s %s}", u.getId(), DotUtils.escapar(u.getNombre()), DotUtils.escapar(u.getApellidos()));
                sb.append(String.format("    u%d [label=\"%s\"];\n", i, label));
                if (temp.getSiguiente() != null) {
                    sb.append(String.format("    u%d -> u%d;\n", i, i + 1));
                }
                temp = temp.getSiguiente();
                i++;
            }
        }
        sb.append("}\n");
        return sb.toString();
    }

    public synchronized String generarDotAlmacen() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph Almacen {\n");
        sb.append("    rankdir=TB;\n");
        sb.append("    node [shape=record, style=filled, fillcolor=\"#FFEBE0\", color=\"#D4763C\", fontname=\"Helvetica\"];\n");

        NodoPilaCaja temp = almacenService.getPilaAlmacen().getTope();
        if (temp == null) {
            sb.append("    vacio [label=\"Almacén Vacío (Pila LIFO)\", shape=box, color=gray];\n");
        } else {
            int i = 1;
            while (temp != null) {
                Caja c = temp.getDato();
                String label = String.format("{Correlativo: %d | Ingreso: %s}", c.getCorrelativo(), c.getFechaIngreso().format(formatter));
                sb.append(String.format("    caja%d [label=\"%s\"];\n", i, label));
                if (temp.getSiguiente() != null) {
                    sb.append(String.format("    caja%d -> caja%d;\n", i, i + 1));
                }
                temp = temp.getSiguiente();
                i++;
            }
        }
        sb.append("}\n");
        return sb.toString();
    }

    public synchronized String generarDotClientes() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph Clientes {\n");
        sb.append("    node [shape=circle, style=filled, fillcolor=\"#FFF6EB\", color=\"#E8A951\", fontname=\"Helvetica\", fixedsize=true, width=2.0];\n");

        NodoAVL raiz = clienteService.getArbolClientes().getRaiz();
        if (raiz == null) {
            sb.append("    vacio [label=\"Árbol AVL de Clientes Vacío\", shape=box, color=gray];\n");
        } else {
            generarDotAVLRec(raiz, sb);
        }
        sb.append("}\n");
        return sb.toString();
    }

    private void generarDotAVLRec(NodoAVL nodo, StringBuilder sb) {
        if (nodo == null) return;

        int factorBalance = getAltura(nodo.getIzquierdo()) - getAltura(nodo.getDerecho());
        String label = String.format("CUI: %s\n%s %s\nAlt: %d | FB: %d",
                nodo.getLlave(),
                DotUtils.escapar(nodo.getDato().getNombre()),
                DotUtils.escapar(nodo.getDato().getApellidos()),
                nodo.getAltura(),
                factorBalance);

        sb.append(String.format("    node_%s [label=\"%s\"];\n", nodo.getLlave(), label));

        if (nodo.getIzquierdo() != null) {
            sb.append(String.format("    node_%s -> node_%s [label=\"I\"];\n", nodo.getLlave(), nodo.getIzquierdo().getLlave()));
            generarDotAVLRec(nodo.getIzquierdo(), sb);
        }
        if (nodo.getDerecho() != null) {
            sb.append(String.format("    node_%s -> node_%s [label=\"D\"];\n", nodo.getLlave(), nodo.getDerecho().getLlave()));
            generarDotAVLRec(nodo.getDerecho(), sb);
        }
    }

    private int getAltura(NodoAVL n) {
        return n == null ? 0 : n.getAltura();
    }

    public synchronized String generarDotRepartidores() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph Repartidores {\n");
        sb.append("    rankdir=LR;\n");
        sb.append("    node [shape=record, style=filled, fillcolor=\"#FFECEB\", color=\"#C94C4C\", fontname=\"Helvetica\"];\n");

        NodoColaRepartidor temp = repartidorService.getColaDisponibles().getFrente();
        if (temp == null) {
            sb.append("    vacio [label=\"Cola de Repartidores Vacía\", shape=box, color=gray];\n");
        } else {
            int i = 1;
            while (temp != null) {
                Repartidor r = temp.getDato();
                String label = String.format("{%s %s | CUI: %s | Licencia: %s}",
                        DotUtils.escapar(r.getNombre()),
                        DotUtils.escapar(r.getApellidos()),
                        r.getCui(),
                        r.getLicencia().name());
                sb.append(String.format("    rep%d [label=\"%s\"];\n", i, label));
                if (i == 1) {
                    sb.append(String.format("    frente [label=\"FRENTE\", shape=none];\n    frente -> rep%d [style=dotted];\n", i));
                }
                if (temp.getSiguiente() != null) {
                    sb.append(String.format("    rep%d -> rep%d;\n", i, i + 1));
                } else {
                    sb.append(String.format("    fin [label=\"FINAL\", shape=none];\n    rep%d -> fin [style=dotted];\n", i));
                }
                temp = temp.getSiguiente();
                i++;
            }
        }
        sb.append("}\n");
        return sb.toString();
    }

    public synchronized String generarDotVehiculos() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph Vehiculos {\n");
        sb.append("    rankdir=LR;\n");
        sb.append("    node [shape=record, style=filled, fillcolor=\"#FFECEB\", color=\"#C94C4C\", fontname=\"Helvetica\"];\n");

        NodoColaVehiculo temp = vehiculoService.getColaDisponibles().getFrente();
        if (temp == null) {
            sb.append("    vacio [label=\"Cola de Vehículos Vacía\", shape=box, color=gray];\n");
        } else {
            int i = 1;
            while (temp != null) {
                Vehiculo v = temp.getDato();
                String label = String.format("{Placa: %s | %s %s | Transmisión: %s}",
                        v.getPlaca(),
                        DotUtils.escapar(v.getMarca()),
                        DotUtils.escapar(v.getModelo()),
                        DotUtils.escapar(v.getTipoTransmision()));
                sb.append(String.format("    veh%d [label=\"%s\"];\n", i, label));
                if (i == 1) {
                    sb.append(String.format("    frente [label=\"FRENTE\", shape=none];\n    frente -> veh%d [style=dotted];\n", i));
                }
                if (temp.getSiguiente() != null) {
                    sb.append(String.format("    veh%d -> veh%d;\n", i, i + 1));
                } else {
                    sb.append(String.format("    fin [label=\"FINAL\", shape=none];\n    veh%d -> fin [style=dotted];\n", i));
                }
                temp = temp.getSiguiente();
                i++;
            }
        }
        sb.append("}\n");
        return sb.toString();
    }

    public synchronized String generarDotPedidos() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph Pedidos {\n");
        sb.append("    rankdir=LR;\n");
        sb.append("    node [shape=record, style=filled, fillcolor=\"#F4F8F3\", color=\"#5D8A4C\", fontname=\"Helvetica\"];\n");

        NodoPedido temp = pedidoService.getListaPedidos().getCabeza();
        if (temp == null) {
            sb.append("    vacio [label=\"Lista de Pedidos Vacía\", shape=box, color=gray];\n");
        } else {
            int i = 1;
            while (temp != null) {
                Pedido p = temp.getDato();
                String label = String.format("{Pedido: #%d | Origen: %s | Destino: %s | Cliente: %s | Repartidor: %s | Vehículo: %s | Cajas: %d | Estado: %s}",
                        p.getNumeroPedido(),
                        p.getDepartamentoOrigen().getNombreLegible(),
                        p.getDepartamentoDestino().getNombreLegible(),
                        DotUtils.escapar(p.getCliente().getNombre()),
                        DotUtils.escapar(p.getRepartidor().getNombre()),
                        p.getVehiculo().getPlaca(),
                        p.getNumeroCajas(),
                        p.getEstado().name());
                sb.append(String.format("    ped%d [label=\"%s\"];\n", i, label));

                // Mostrar conexión de cajas del pedido como una mini-lista vertical
                NodoCaja cTemp = p.getCajas().getCabeza();
                if (cTemp != null) {
                    sb.append(String.format("    subgraph cluster_cajas%d {\n", i));
                    sb.append("        label=\"Cajas del Pedido\";\n");
                    sb.append("        style=dotted;\n");
                    sb.append("        color=\"#5D8A4C\";\n");
                    int k = 1;
                    while (cTemp != null) {
                        Caja c = cTemp.getDato();
                        sb.append(String.format("        ped%d_caja%d [label=\"Caja #%d\", shape=box, style=filled, fillcolor=\"#E2EFE0\"];\n", i, k, c.getCorrelativo()));
                        if (cTemp.getSiguiente() != null) {
                            sb.append(String.format("        ped%d_caja%d -> ped%d_caja%d;\n", i, k, i, k + 1));
                        }
                        cTemp = cTemp.getSiguiente();
                        k++;
                    }
                    sb.append("    }\n");
                    // Conectar el nodo del pedido con el primer nodo de cajas
                    sb.append(String.format("    ped%d -> ped%d_caja1 [style=dashed];\n", i, i));
                }

                if (temp.getSiguiente() != null) {
                    sb.append(String.format("    ped%d -> ped%d;\n", i, i + 1));
                }
                temp = temp.getSiguiente();
                i++;
            }
        }
        sb.append("}\n");
        return sb.toString();
    }

    public synchronized String generarDotEstructuraGeneral() {
        // Combina todas las estructuras en un único gráfico usando subgrafos (clusters)
        StringBuilder sb = new StringBuilder();
        sb.append("digraph EstructuraGeneral {\n");
        sb.append("    compound=true;\n");
        sb.append("    fontname=\"Helvetica\";\n");

        // Subgrafo Usuarios
        sb.append("    subgraph cluster_usr {\n");
        sb.append("        label=\"USUARIOS (Lista Enlazada)\";\n");
        sb.append("        style=filled; color=\"#E8D5C0\"; fillcolor=\"#FFFBF7\";\n");
        sb.append("        usr_rank [style=invis, label=\"\"];\n");
        NodoUsuario uTemp = usuarioService.getListaUsuarios().getCabeza();
        if (uTemp == null) {
            sb.append("        usr_vacio [label=\"Lista Vacía\", shape=box];\n");
        } else {
            int i = 1;
            while (uTemp != null) {
                Usuario u = uTemp.getDato();
                sb.append(String.format("        g_usr%d [label=\"ID: %d\\n%s\", shape=record, fillcolor=\"#FFEBE0\", style=filled];\n", i, u.getId(), DotUtils.escapar(u.getNombre())));
                if (uTemp.getSiguiente() != null) {
                    sb.append(String.format("        g_usr%d -> g_usr%d;\n", i, i + 1));
                }
                uTemp = uTemp.getSiguiente();
                i++;
            }
        }
        sb.append("    }\n");

        // Subgrafo Almacen
        sb.append("    subgraph cluster_alm {\n");
        sb.append("        label=\"ALMACÉN (Pila LIFO)\";\n");
        sb.append("        style=filled; color=\"#E8D5C0\"; fillcolor=\"#FFFBF7\";\n");
        NodoPilaCaja cTemp = almacenService.getPilaAlmacen().getTope();
        if (cTemp == null) {
            sb.append("        alm_vacio [label=\"Pila Vacía\", shape=box];\n");
        } else {
            int i = 1;
            while (cTemp != null && i <= 5) { // Mostrar un máximo de 5 para no saturar
                Caja c = cTemp.getDato();
                sb.append(String.format("        g_caja%d [label=\"Caja #%d\", shape=record, fillcolor=\"#FFEBE0\", style=filled];\n", i, c.getCorrelativo()));
                if (cTemp.getSiguiente() != null && i < 5) {
                    sb.append(String.format("        g_caja%d -> g_caja%d;\n", i, i + 1));
                }
                cTemp = cTemp.getSiguiente();
                i++;
            }
            if (cTemp != null) {
                sb.append("        g_caja_mas [label=\"...\", shape=none];\n");
                sb.append("        g_caja5 -> g_caja_mas;\n");
            }
        }
        sb.append("    }\n");

        // Subgrafo Clientes
        sb.append("    subgraph cluster_cli {\n");
        sb.append("        label=\"CLIENTES (Árbol AVL)\";\n");
        sb.append("        style=filled; color=\"#E8D5C0\"; fillcolor=\"#FFFBF7\";\n");
        NodoAVL rCli = clienteService.getArbolClientes().getRaiz();
        if (rCli == null) {
            sb.append("        cli_vacio [label=\"Árbol Vacío\", shape=box];\n");
        } else {
            generarDotAVLRecCluster(rCli, sb);
        }
        sb.append("    }\n");

        // Subgrafo Repartidores
        sb.append("    subgraph cluster_rep {\n");
        sb.append("        label=\"REPARTIDORES (Cola FIFO)\";\n");
        sb.append("        style=filled; color=\"#E8D5C0\"; fillcolor=\"#FFFBF7\";\n");
        NodoColaRepartidor repTemp = repartidorService.getColaDisponibles().getFrente();
        if (repTemp == null) {
            sb.append("        rep_vacio [label=\"Cola Vacía\", shape=box];\n");
        } else {
            int i = 1;
            while (repTemp != null) {
                Repartidor r = repTemp.getDato();
                sb.append(String.format("        g_rep%d [label=\"%s\\nCUI: %s\", shape=record, fillcolor=\"#FFECEB\", style=filled];\n", i, DotUtils.escapar(r.getNombre()), r.getCui()));
                if (repTemp.getSiguiente() != null) {
                    sb.append(String.format("        g_rep%d -> g_rep%d;\n", i, i + 1));
                }
                repTemp = repTemp.getSiguiente();
                i++;
            }
        }
        sb.append("    }\n");

        // Subgrafo Vehículos
        sb.append("    subgraph cluster_veh {\n");
        sb.append("        label=\"VEHÍCULOS (Cola FIFO)\";\n");
        sb.append("        style=filled; color=\"#E8D5C0\"; fillcolor=\"#FFFBF7\";\n");
        NodoColaVehiculo vehTemp = vehiculoService.getColaDisponibles().getFrente();
        if (vehTemp == null) {
            sb.append("        veh_vacio [label=\"Cola Vacía\", shape=box];\n");
        } else {
            int i = 1;
            while (vehTemp != null) {
                Vehiculo v = vehTemp.getDato();
                sb.append(String.format("        g_veh%d [label=\"%s\\n%s\", shape=record, fillcolor=\"#FFECEB\", style=filled];\n", i, v.getPlaca(), DotUtils.escapar(v.getMarca())));
                if (vehTemp.getSiguiente() != null) {
                    sb.append(String.format("        g_veh%d -> g_veh%d;\n", i, i + 1));
                }
                vehTemp = vehTemp.getSiguiente();
                i++;
            }
        }
        sb.append("    }\n");

        sb.append("}\n");
        return sb.toString();
    }

    private void generarDotAVLRecCluster(NodoAVL nodo, StringBuilder sb) {
        if (nodo == null) return;
        sb.append(String.format("        g_cli_%s [label=\"CUI: %s\\n%s\", shape=circle, fillcolor=\"#FFF6EB\", style=filled, fixedsize=true, width=1.5];\n",
                nodo.getLlave(), nodo.getLlave(), DotUtils.escapar(nodo.getDato().getNombre())));
        if (nodo.getIzquierdo() != null) {
            sb.append(String.format("        g_cli_%s -> g_cli_%s;\n", nodo.getLlave(), nodo.getIzquierdo().getLlave()));
            generarDotAVLRecCluster(nodo.getIzquierdo(), sb);
        }
        if (nodo.getDerecho() != null) {
            sb.append(String.format("        g_cli_%s -> g_cli_%s;\n", nodo.getLlave(), nodo.getDerecho().getLlave()));
            generarDotAVLRecCluster(nodo.getDerecho(), sb);
        }
    }
}
