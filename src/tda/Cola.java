package tda;

import tda.interfaces.ICola;

public class Cola<T> implements ICola<T> {

    private Nodo cabeza;
    private Nodo ultimo;
    private int cantElementos;

    private class Nodo {
        T elemento;
        Nodo siguiente;

        Nodo(T elemento) {
            this.elemento = elemento;
        }
    }

    @Override
    public void encolar(T elemento) {
        if (elemento == null) throw new IllegalArgumentException();
        Nodo nodo = new Nodo(elemento);
        if (ultimo == null) {
            cabeza = ultimo = nodo;
        } else {
            ultimo.siguiente = nodo;
            ultimo = nodo;
        }
        cantElementos++;
    }

    @Override
    public T desencolar() {
        if (estaVacia()) throw new IllegalStateException();
        T elemento = cabeza.elemento;
        cabeza = cabeza.siguiente;
        if (cabeza == null) ultimo = null;
        cantElementos--;
        return elemento;
    }

    @Override
    public T frente() {
        if (estaVacia()) throw new IllegalStateException();
        return cabeza.elemento;
    }

    @Override
    public boolean estaVacia() {
        return cabeza == null;
    }

    @Override
    public int tamanio() {
        return cantElementos;
    }
}
