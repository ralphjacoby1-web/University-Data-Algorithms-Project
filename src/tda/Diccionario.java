package tda;

import tda.interfaces.IDiccionario;

public class Diccionario<K, V> implements IDiccionario<K, V> {

    private static final int CAPACIDAD = 16;
    private Object[] cubetas;
    private int cantElementos;

    private static class Entrada<K, V> {
        K clave;
        V valor;
        Entrada<K, V> siguiente;

        Entrada(K clave, V valor) {
            this.clave = clave;
            this.valor = valor;
        }
    }

    public Diccionario() {
        cubetas = new Object[CAPACIDAD];
    }

    private int indice(K clave) {
        return Math.abs(clave.hashCode() % CAPACIDAD);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void poner(K clave, V valor) {
        int i = indice(clave);
        Entrada<K, V> entrada = (Entrada<K, V>) cubetas[i];
        while (entrada != null) {
            if (entrada.clave.equals(clave)) {
                entrada.valor = valor;
                return;
            }
            entrada = entrada.siguiente;
        }
        Entrada<K, V> nuevaEntrada = new Entrada<>(clave, valor);
        nuevaEntrada.siguiente = (Entrada<K, V>) cubetas[i];
        cubetas[i] = nuevaEntrada;
        cantElementos++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V obtener(K clave) {
        int i = indice(clave);
        Entrada<K, V> entrada = (Entrada<K, V>) cubetas[i];
        while (entrada != null) {
            if (entrada.clave.equals(clave)) return entrada.valor;
            entrada = entrada.siguiente;
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void eliminar(K clave) {
        int i = indice(clave);
        Entrada<K, V> entrada = (Entrada<K, V>) cubetas[i];
        Entrada<K, V> anterior = null;
        while (entrada != null) {
            if (entrada.clave.equals(clave)) {
                if (anterior == null) cubetas[i] = entrada.siguiente;
                else anterior.siguiente = entrada.siguiente;
                cantElementos--;
                return;
            }
            anterior = entrada;
            entrada = entrada.siguiente;
        }
    }

    @Override
    public boolean contieneClave(K clave) {
        return obtener(clave) != null;
    }

    @Override
    public boolean estaVacio() {
        return cantElementos == 0;
    }

    @Override
    public int tamanio() {
        return cantElementos;
    }
}
