package tda;

import tda.interfaces.IPriorityQueue;

public class PriorityQueue<T> implements IPriorityQueue<T> {

    private static class NodoPrioridad<T> {

        T elemento;
        int prioridad;
        NodoPrioridad<T> siguiente;

        public NodoPrioridad(T dato, int prioridad) {

            this.elemento = dato;
            this.prioridad = prioridad;
            this.siguiente = null;

        }

    }

    private NodoPrioridad<T> frente;
    private int tamanio;

    public PriorityQueue() {

        this.frente = null;
        this.tamanio = 0;

    }

    @Override
    public void encolar(T elemento, int prioridad) {
        if (elemento == null || prioridad <= 0) { return; }

        NodoPrioridad<T> nuevoNodo = new NodoPrioridad<>(elemento, prioridad);

        if (frente == null || prioridad > frente.prioridad) {

            nuevoNodo.siguiente = frente;
            frente = nuevoNodo;

        } else {

            NodoPrioridad<T> nodoActual = frente;

            while (nodoActual.siguiente != null && nodoActual.siguiente.prioridad >= prioridad) {
                nodoActual = nodoActual.siguiente;
            }

            nuevoNodo.siguiente = nodoActual.siguiente;
            nodoActual.siguiente = nuevoNodo;

        }

        tamanio++;
    }

    @Override
    public T desencolar() {

        if (estaVacio()) { return null; }

        T elementoRetornar = frente.elemento;
        frente = frente.siguiente;
        tamanio--;

        return elementoRetornar;
    }

    @Override
    public T verFrente() {
        return !estaVacio() ? frente.elemento : null;
    }

    @Override
    public int verTamanio() {
        return tamanio;
    }

    @Override
    public boolean estaVacio() {
        return frente == null;
    }

}
