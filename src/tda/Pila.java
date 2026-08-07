package tda;

import tda.interfaces.IPila;

public class Pila<T> implements IPila<T> {

    private Nodo cima;
    private int cantNodos;

    private class Nodo {
        T elemento;
        Nodo siguiente;

        Nodo(T elemento) {
            this.elemento = elemento;
            siguiente = null;
        }
    }

    @Override
    public void apilar(T elemento) {
        if (elemento == null) throw new IllegalStateException();
        Nodo nodo = new Nodo(elemento);
        nodo.siguiente = cima;
        cima = nodo;
        cantNodos++;
    }

    @Override
    public T desapilar() {
        if (estaVacia()) throw new IllegalStateException();
        T elemento = cima.elemento;
        cima = cima.siguiente;
        cantNodos--;
        return elemento;
    }

    @Override
    public T tope() {
        return cima.elemento;
    }

    @Override
    public boolean estaVacia() {
        return cima == null;
    }

    @Override
    public int tamanio() {
        return cantNodos;
    }
}
