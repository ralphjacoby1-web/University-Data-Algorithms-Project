package tda;

import tda.interfaces.IAbb;

public class Abb<T extends Comparable<T>> implements IAbb<T> {

    private Nodo raiz;

    private class Nodo {
        T elemento;
        Nodo izquierda, derecha;

        Nodo(T elemento) {
            this.elemento = elemento;
        }
    }

    @Override
    public void insertar(T elemento) {
        if (elemento == null) throw new IllegalArgumentException();
        raiz = insertarRec(raiz, elemento);
    }

    private Nodo insertarRec(Nodo nodo, T elemento) {
        if (nodo == null) return new Nodo(elemento);
        int cmp = elemento.compareTo(nodo.elemento);
        if (cmp < 0) nodo.izquierda = insertarRec(nodo.izquierda, elemento);
        else if (cmp > 0) nodo.derecha = insertarRec(nodo.derecha, elemento);
        return nodo;
    }

    @Override
    public void eliminar(T elemento) {
        raiz = eliminarRec(raiz, elemento);
    }

    private Nodo eliminarRec(Nodo nodo, T elemento) {
        if (nodo == null) return null;
        int cmp = elemento.compareTo(nodo.elemento);
        if (cmp < 0) nodo.izquierda = eliminarRec(nodo.izquierda, elemento);
        else if (cmp > 0) nodo.derecha = eliminarRec(nodo.derecha, elemento);
        else {
            if (nodo.izquierda == null) return nodo.derecha;
            if (nodo.derecha == null) return nodo.izquierda;
            Nodo sucesor = nodoMinimo(nodo.derecha);
            nodo.elemento = sucesor.elemento;
            nodo.derecha = eliminarRec(nodo.derecha, sucesor.elemento);
        }
        return nodo;
    }

    @Override
    public boolean buscar(T elemento) {
        return buscarRec(raiz, elemento);
    }

    private boolean buscarRec(Nodo nodo, T elemento) {
        if (nodo == null) return false;
        int cmp = elemento.compareTo(nodo.elemento);
        if (cmp == 0) return true;
        return cmp < 0 ? buscarRec(nodo.izquierda, elemento) : buscarRec(nodo.derecha, elemento);
    }

    @Override
    public void enOrden() {
        enOrdenRec(raiz);
        System.out.println();
    }

    private void enOrdenRec(Nodo nodo) {
        if (nodo == null) return;
        enOrdenRec(nodo.izquierda);
        System.out.print(nodo.elemento + "  ");
        enOrdenRec(nodo.derecha);
    }

    @Override
    public T maximo() {
        if (raiz == null) return null;
        Nodo n = raiz;
        while (n.derecha != null) n = n.derecha;
        return n.elemento;
    }

    @Override
    public T minimo() {
        if (raiz == null) return null;
        return nodoMinimo(raiz).elemento;
    }

    private Nodo nodoMinimo(Nodo nodo) {
        while (nodo.izquierda != null) nodo = nodo.izquierda;
        return nodo;
    }
}
