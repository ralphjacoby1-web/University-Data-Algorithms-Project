package tda;

import tda.interfaces.IColaPrioridad;

public class ColaPrioridad<T> implements IColaPrioridad<T> {

    private static class NodoPrioridad<T> {
        T elemento;
        int prioridad;
        NodoPrioridad<T> siguiente;

        NodoPrioridad(T elemento, int prioridad) {
            this.elemento = elemento;
            this.prioridad = prioridad;
            this.siguiente = null;
        }
    }

    private NodoPrioridad<T> frente;
    private int tamanio;

    public ColaPrioridad() {
        this.frente = null;
        this.tamanio = 0;
    }

    @Override
    public void encolar(T elemento, int prioridad) {
        if (elemento == null || prioridad <= 0) return;
        NodoPrioridad<T> nuevoNodo = new NodoPrioridad<>(elemento, prioridad);
        if (frente == null || prioridad > frente.prioridad) {
            nuevoNodo.siguiente = frente;
            frente = nuevoNodo;
        } else {
            NodoPrioridad<T> actual = frente;
            while (actual.siguiente != null && actual.siguiente.prioridad >= prioridad)
                actual = actual.siguiente;
            nuevoNodo.siguiente = actual.siguiente;
            actual.siguiente = nuevoNodo;
        }
        tamanio++;
    }

    @Override
    public T desencolar() {
        if (estaVacio()) return null;
        T elemento = frente.elemento;
        frente = frente.siguiente;
        tamanio--;
        return elemento;
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
