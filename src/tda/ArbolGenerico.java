package tda;

import tda.interfaces.IArbolGenerico;

public class ArbolGenerico<T> implements IArbolGenerico<T> {

    private Nodo raiz;

    private class Nodo {
        T valor;
        Nodo primerHijo;
        Nodo siguienteHermano;

        Nodo(T valor) {
            this.valor = valor;
        }
    }

    @Override
    public void agregarRaiz(T elemento) {
        if (raiz == null) raiz = new Nodo(elemento);
    }

    @Override
    public void agregarHijo(T padre, T hijo) {
        Nodo nodoPadre = buscarNodo(raiz, padre);
        if (nodoPadre == null) return;
        Nodo nodoHijo = new Nodo(hijo);
        if (nodoPadre.primerHijo == null) {
            nodoPadre.primerHijo = nodoHijo;
        } else {
            Nodo hermano = nodoPadre.primerHijo;
            while (hermano.siguienteHermano != null) hermano = hermano.siguienteHermano;
            hermano.siguienteHermano = nodoHijo;
        }
    }

    private Nodo buscarNodo(Nodo nodo, T valor) {
        if (nodo == null) return null;
        if (nodo.valor.equals(valor)) return nodo;
        Nodo encontrado = buscarNodo(nodo.primerHijo, valor);
        if (encontrado != null) return encontrado;
        return buscarNodo(nodo.siguienteHermano, valor);
    }

    @Override
    public void recorridoProfundidad() {
        profundidadRec(raiz, 0);
    }

    private void profundidadRec(Nodo nodo, int profundidad) {
        if (nodo == null) return;
        for (int i = 0; i < profundidad; i++) System.out.print("  ");
        System.out.println(nodo.valor);
        Nodo hijo = nodo.primerHijo;
        while (hijo != null) {
            profundidadRec(hijo, profundidad + 1);
            hijo = hijo.siguienteHermano;
        }
    }

    @Override
    public void recorridoAmplitud() {
        if (raiz == null) return;
        Cola<Nodo> cola = new Cola<>();
        cola.encolar(raiz);
        while (!cola.estaVacia()) {
            Nodo actual = cola.desencolar();
            System.out.print(actual.valor + "  ");
            Nodo hijo = actual.primerHijo;
            while (hijo != null) {
                cola.encolar(hijo);
                hijo = hijo.siguienteHermano;
            }
        }
        System.out.println();
    }
}
