package tda;

import tda.interfaces.IArbolB;

public class ArbolB implements IArbolB {

    private static final int T = 2;

    private NodoB raiz;

    private class NodoB {
        int[] claves;
        NodoB[] hijos;
        int cantClaves;
        boolean esHoja;

        NodoB(boolean esHoja) {
            this.esHoja = esHoja;
            this.claves = new int[2 * T - 1];
            this.hijos = new NodoB[2 * T];
            this.cantClaves = 0;
        }
    }

    public ArbolB() {
        raiz = new NodoB(true);
    }

    @Override
    public boolean buscar(int clave) {
        return buscarRec(raiz, clave);
    }

    private boolean buscarRec(NodoB nodo, int clave) {
        int i = 0;
        while (i < nodo.cantClaves && clave > nodo.claves[i]) i++;
        if (i < nodo.cantClaves && clave == nodo.claves[i]) return true;
        if (nodo.esHoja) return false;
        return buscarRec(nodo.hijos[i], clave);
    }

    @Override
    public void insertar(int clave) {
        NodoB r = raiz;
        if (r.cantClaves == 2 * T - 1) {
            NodoB s = new NodoB(false);
            raiz = s;
            s.hijos[0] = r;
            dividirHijo(s, 0, r);
            insertarEnNodoNoLleno(s, clave);
        } else {
            insertarEnNodoNoLleno(r, clave);
        }
    }

    private void insertarEnNodoNoLleno(NodoB nodo, int clave) {
        int i = nodo.cantClaves - 1;
        if (nodo.esHoja) {
            while (i >= 0 && clave < nodo.claves[i]) {
                nodo.claves[i + 1] = nodo.claves[i];
                i--;
            }
            nodo.claves[i + 1] = clave;
            nodo.cantClaves++;
        } else {
            while (i >= 0 && clave < nodo.claves[i]) i--;
            i++;
            if (nodo.hijos[i].cantClaves == 2 * T - 1) {
                dividirHijo(nodo, i, nodo.hijos[i]);
                if (clave > nodo.claves[i]) i++;
            }
            insertarEnNodoNoLleno(nodo.hijos[i], clave);
        }
    }

    private void dividirHijo(NodoB padre, int i, NodoB hijo) {
        NodoB nuevoNodo = new NodoB(hijo.esHoja);
        nuevoNodo.cantClaves = T - 1;
        for (int j = 0; j < T - 1; j++)
            nuevoNodo.claves[j] = hijo.claves[j + T];
        if (!hijo.esHoja)
            for (int j = 0; j < T; j++)
                nuevoNodo.hijos[j] = hijo.hijos[j + T];
        hijo.cantClaves = T - 1;
        for (int j = padre.cantClaves; j >= i + 1; j--)
            padre.hijos[j + 1] = padre.hijos[j];
        padre.hijos[i + 1] = nuevoNodo;
        for (int j = padre.cantClaves - 1; j >= i; j--)
            padre.claves[j + 1] = padre.claves[j];
        padre.claves[i] = hijo.claves[T - 1];
        padre.cantClaves++;
    }

    @Override
    public void enOrden() {
        enOrdenRec(raiz);
        System.out.println();
    }

    private void enOrdenRec(NodoB nodo) {
        if (nodo == null) return;
        int i;
        for (i = 0; i < nodo.cantClaves; i++) {
            if (!nodo.esHoja) enOrdenRec(nodo.hijos[i]);
            System.out.print(nodo.claves[i] + "  ");
        }
        if (!nodo.esHoja) enOrdenRec(nodo.hijos[i]);
    }
}
