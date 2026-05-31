package com.proyectofinal.jrmg.structures.avl;

import com.proyectofinal.jrmg.model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ArbolAVL {
    private NodoAVL raiz;
    private int tamanio;

    public ArbolAVL() {
        this.raiz = null;
        this.tamanio = 0;
    }

    private int altura(NodoAVL n) {
        return n == null ? 0 : n.getAltura();
    }

    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    private int getFactorBalance(NodoAVL n) {
        return n == null ? 0 : altura(n.getIzquierdo()) - altura(n.getDerecho());
    }

    private NodoAVL rotarDerecha(NodoAVL y) {
        NodoAVL x = y.getIzquierdo();
        NodoAVL T2 = x.getDerecho();

        x.setDerecho(y);
        y.setIzquierdo(T2);

        y.setAltura(max(altura(y.getIzquierdo()), altura(y.getDerecho())) + 1);
        x.setAltura(max(altura(x.getIzquierdo()), altura(x.getDerecho())) + 1);

        return x;
    }

    private NodoAVL rotarIzquierda(NodoAVL x) {
        NodoAVL y = x.getDerecho();
        NodoAVL T2 = y.getIzquierdo();

        y.setIzquierdo(x);
        x.setDerecho(T2);

        x.setAltura(max(altura(x.getIzquierdo()), altura(x.getDerecho())) + 1);
        y.setAltura(max(altura(y.getIzquierdo()), altura(y.getDerecho())) + 1);

        return y;
    }

    public synchronized void insertar(Cliente cliente) {
        raiz = insertarRec(raiz, cliente);
    }

    private NodoAVL insertarRec(NodoAVL nodo, Cliente cliente) {
        if (nodo == null) {
            tamanio++;
            return new NodoAVL(cliente);
        }

        // CUI es String, usamos compareTo
        int comparacion = cliente.getCui().compareTo(nodo.getLlave());

        if (comparacion < 0) {
            nodo.setIzquierdo(insertarRec(nodo.getIzquierdo(), cliente));
        } else if (comparacion > 0) {
            nodo.setDerecho(insertarRec(nodo.getDerecho(), cliente));
        } else {
            // Ya existe, actualizamos los datos
            nodo.setDato(cliente);
            return nodo;
        }

        nodo.setAltura(1 + max(altura(nodo.getIzquierdo()), altura(nodo.getDerecho())));

        int balance = getFactorBalance(nodo);

        // Caso Izquierda Izquierda
        if (balance > 1 && cliente.getCui().compareTo(nodo.getIzquierdo().getLlave()) < 0) {
            return rotarDerecha(nodo);
        }

        // Caso Derecha Derecha
        if (balance < -1 && cliente.getCui().compareTo(nodo.getDerecho().getLlave()) > 0) {
            return rotarIzquierda(nodo);
        }

        // Caso Izquierda Derecha
        if (balance > 1 && cliente.getCui().compareTo(nodo.getIzquierdo().getLlave()) > 0) {
            nodo.setIzquierdo(rotarIzquierda(nodo.getIzquierdo()));
            return rotarDerecha(nodo);
        }

        // Caso Derecha Izquierda
        if (balance < -1 && cliente.getCui().compareTo(nodo.getDerecho().getLlave()) < 0) {
            nodo.setDerecho(rotarDerecha(nodo.getDerecho()));
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    public synchronized Cliente buscar(String cui) {
        NodoAVL nodo = buscarRec(raiz, cui);
        return nodo == null ? null : nodo.getDato();
    }

    private NodoAVL buscarRec(NodoAVL nodo, String cui) {
        if (nodo == null || nodo.getLlave().equals(cui)) {
            return nodo;
        }

        int comparacion = cui.compareTo(nodo.getLlave());
        if (comparacion < 0) {
            return buscarRec(nodo.getIzquierdo(), cui);
        }
        return buscarRec(nodo.getDerecho(), cui);
    }

    public synchronized boolean modificar(Cliente cliente) {
        NodoAVL nodo = buscarRec(raiz, cliente.getCui());
        if (nodo != null) {
            nodo.setDato(cliente);
            return true;
        }
        return false;
    }

    public synchronized void eliminar(String cui) {
        raiz = eliminarRec(raiz, cui);
    }

    private NodoAVL eliminarRec(NodoAVL root, String cui) {
        if (root == null) {
            return root;
        }

        int comparacion = cui.compareTo(root.getLlave());

        if (comparacion < 0) {
            root.setIzquierdo(eliminarRec(root.getIzquierdo(), cui));
        } else if (comparacion > 0) {
            root.setDerecho(eliminarRec(root.getDerecho(), cui));
        } else {
            // Nodo encontrado
            tamanio--;
            if ((root.getIzquierdo() == null) || (root.getDerecho() == null)) {
                NodoAVL temp = null;
                if (temp == root.getIzquierdo()) {
                    temp = root.getDerecho();
                } else {
                    temp = root.getIzquierdo();
                }

                if (temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp; // Copiar el contenido del hijo no vacío
                }
            } else {
                // Nodo con dos hijos, obtener el sucesor inorden
                NodoAVL temp = getNodoConValorMinimo(root.getDerecho());

                // Copiar los datos del sucesor
                root.setLlave(temp.getLlave());
                root.setDato(temp.getDato());

                // Eliminar el sucesor inorden
                root.setDerecho(eliminarRec(root.getDerecho(), temp.getLlave()));
            }
        }

        if (root == null) {
            return root;
        }

        root.setAltura(max(altura(root.getIzquierdo()), altura(root.getDerecho())) + 1);

        int balance = getFactorBalance(root);

        // Caso Izquierda Izquierda
        if (balance > 1 && getFactorBalance(root.getIzquierdo()) >= 0) {
            return rotarDerecha(root);
        }

        // Caso Izquierda Derecha
        if (balance > 1 && getFactorBalance(root.getIzquierdo()) < 0) {
            root.setIzquierdo(rotarIzquierda(root.getIzquierdo()));
            return rotarDerecha(root);
        }

        // Caso Derecha Derecha
        if (balance < -1 && getFactorBalance(root.getDerecho()) <= 0) {
            return rotarIzquierda(root);
        }

        // Caso Derecha Izquierda
        if (balance < -1 && getFactorBalance(root.getDerecho()) > 0) {
            root.setDerecho(rotarDerecha(root.getDerecho()));
            return rotarIzquierda(root);
        }

        return root;
    }

    private NodoAVL getNodoConValorMinimo(NodoAVL nodo) {
        NodoAVL actual = nodo;
        while (actual.getIzquierdo() != null) {
            actual = actual.getIzquierdo();
        }
        return actual;
    }

    public synchronized int tamanio() {
        return tamanio;
    }

    public synchronized List<Cliente> obtenerTodos() {
        List<Cliente> clientes = new ArrayList<>();
        obtenerTodosRec(raiz, clientes);
        return clientes;
    }

    private void obtenerTodosRec(NodoAVL nodo, List<Cliente> clientes) {
        if (nodo != null) {
            obtenerTodosRec(nodo.getIzquierdo(), clientes);
            clientes.add(nodo.getDato());
            obtenerTodosRec(nodo.getDerecho(), clientes);
        }
    }

    public synchronized NodoAVL getRaiz() {
        return raiz;
    }
}
