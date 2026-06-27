package tda;

import tda.interfaces.IAvl;

public class Avl<T extends Comparable<T>> implements IAvl<T> {

    private Nodo raiz;

    private class Nodo {
        T elemento;
        Nodo izquierda, derecha;
        int altura;

        Nodo(T elemento) {
            this.elemento = elemento;
            this.altura = 1;
        }
    }

    private int altura(Nodo nodo) {
        return nodo == null ? 0 : nodo.altura;
    }

    private void actualizarAltura(Nodo nodo) {
        nodo.altura = 1 + Math.max(altura(nodo.izquierda), altura(nodo.derecha));
    }

    private int factorBalance(Nodo nodo) {
        return nodo == null ? 0 : altura(nodo.izquierda) - altura(nodo.derecha);
    }

    private Nodo rotarDerecha(Nodo y) {
        Nodo x = y.izquierda;
        Nodo t = x.derecha;
        x.derecha = y;
        y.izquierda = t;
        actualizarAltura(y);
        actualizarAltura(x);
        return x;
    }

    private Nodo rotarIzquierda(Nodo x) {
        Nodo y = x.derecha;
        Nodo t = y.izquierda;
        y.izquierda = x;
        x.derecha = t;
        actualizarAltura(x);
        actualizarAltura(y);
        return y;
    }

    private Nodo rebalancear(Nodo nodo) {
        actualizarAltura(nodo);
        int fb = factorBalance(nodo);
        if (fb > 1) {
            if (factorBalance(nodo.izquierda) < 0)
                nodo.izquierda = rotarIzquierda(nodo.izquierda);
            return rotarDerecha(nodo);
        }
        if (fb < -1) {
            if (factorBalance(nodo.derecha) > 0)
                nodo.derecha = rotarDerecha(nodo.derecha);
            return rotarIzquierda(nodo);
        }
        return nodo;
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
        else return nodo;
        return rebalancear(nodo);
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
        return rebalancear(nodo);
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

    public int obtenerAltura() {
        return altura(raiz);
    }

    public int obtenerFactorBalance(T elemento) {
        Nodo nodo = encontrarNodo(raiz, elemento);
        return nodo == null ? 0 : factorBalance(nodo);
    }

    private Nodo encontrarNodo(Nodo nodo, T elemento) {
        if (nodo == null) return null;
        int cmp = elemento.compareTo(nodo.elemento);
        if (cmp == 0) return nodo;
        return cmp < 0 ? encontrarNodo(nodo.izquierda, elemento) : encontrarNodo(nodo.derecha, elemento);
    }
}
