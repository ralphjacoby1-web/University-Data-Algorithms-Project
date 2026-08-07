package tda;

import tda.interfaces.IGrafo;

public class Grafo implements IGrafo {

    private static final int MAX_VERTICES = 50;
    private String[] vertices;
    private boolean[][] matrizAdyacencia;
    private int cantVertices;

    public Grafo() {
        vertices         = new String[MAX_VERTICES];
        matrizAdyacencia = new boolean[MAX_VERTICES][MAX_VERTICES];
        cantVertices     = 0;
    }

    private int indiceDe(String vertice) {
        for (int i = 0; i < cantVertices; i++)
            if (vertices[i].equals(vertice)) return i;
        return -1;
    }

    @Override
    public void agregarVertice(String vertice) {
        if (indiceDe(vertice) != -1 || cantVertices >= MAX_VERTICES) return;
        vertices[cantVertices++] = vertice;
    }

    @Override
    public void agregarArista(String origen, String destino) {
        int o = indiceDe(origen), d = indiceDe(destino);
        if (o == -1 || d == -1) return;
        matrizAdyacencia[o][d] = true;
    }

    @Override
    public void recorridoAmplitud(String inicio) {
        int s = indiceDe(inicio);
        if (s == -1) { System.out.println("Vertice no encontrado: " + inicio); return; }
        boolean[] visitados = new boolean[cantVertices];
        Cola<Integer> cola = new Cola<>();
        visitados[s] = true;
        cola.encolar(s);
        System.out.print("BFS desde " + inicio + ": ");
        while (!cola.estaVacia()) {
            int v = cola.desencolar();
            System.out.print(vertices[v] + "  ");
            for (int i = 0; i < cantVertices; i++) {
                if (matrizAdyacencia[v][i] && !visitados[i]) {
                    visitados[i] = true;
                    cola.encolar(i);
                }
            }
        }
        System.out.println();
    }

    @Override
    public void recorridoProfundidad(String inicio) {
        int s = indiceDe(inicio);
        if (s == -1) { System.out.println("Vertice no encontrado: " + inicio); return; }
        boolean[] visitados = new boolean[cantVertices];
        System.out.print("DFS desde " + inicio + ": ");
        profundidadRec(s, visitados);
        System.out.println();
    }

    private void profundidadRec(int v, boolean[] visitados) {
        visitados[v] = true;
        System.out.print(vertices[v] + "  ");
        for (int i = 0; i < cantVertices; i++)
            if (matrizAdyacencia[v][i] && !visitados[i])
                profundidadRec(i, visitados);
    }

    @Override
    public boolean existeCamino(String origen, String destino) {
        int o = indiceDe(origen), d = indiceDe(destino);
        if (o == -1 || d == -1) return false;
        boolean[] visitados = new boolean[cantVertices];
        return existeCaminoRec(o, d, visitados);
    }

    private boolean existeCaminoRec(int origen, int destino, boolean[] visitados) {
        if (origen == destino) return true;
        visitados[origen] = true;
        for (int i = 0; i < cantVertices; i++)
            if (matrizAdyacencia[origen][i] && !visitados[i])
                if (existeCaminoRec(i, destino, visitados)) return true;
        return false;
    }

    // BFS inverso: retorna todos los vertices que son prerequisito (directo o transitivo) de 'destino'
    public String[] prerequisitosTransitivos(String destino) {
        int d = indiceDe(destino);
        if (d == -1) return new String[0];
        boolean[] alcanzable = new boolean[cantVertices];
        Cola<Integer> cola = new Cola<>();
        alcanzable[d] = true;
        cola.encolar(d);
        while (!cola.estaVacia()) {
            int v = cola.desencolar();
            for (int i = 0; i < cantVertices; i++) {
                if (matrizAdyacencia[i][v] && !alcanzable[i]) {
                    alcanzable[i] = true;
                    cola.encolar(i);
                }
            }
        }
        int count = 0;
        for (int i = 0; i < cantVertices; i++)
            if (alcanzable[i] && i != d) count++;
        String[] resultado = new String[count];
        int idx = 0;
        for (int i = 0; i < cantVertices; i++)
            if (alcanzable[i] && i != d) resultado[idx++] = vertices[i];
        return resultado;
    }

    // BFS con reconstruccion del camino via Pila; retorna array vacio si no existe camino
    public String[] caminoAmplitud(String origen, String destino) {
        int s = indiceDe(origen), t = indiceDe(destino);
        if (s == -1 || t == -1) return new String[0];
        if (s == t) return new String[]{vertices[s]};
        boolean[] visitados = new boolean[cantVertices];
        int[] padre = new int[cantVertices];
        for (int i = 0; i < cantVertices; i++) padre[i] = -1;
        Cola<Integer> cola = new Cola<>();
        visitados[s] = true;
        cola.encolar(s);
        boolean encontrado = false;
        while (!cola.estaVacia() && !encontrado) {
            int v = cola.desencolar();
            for (int i = 0; i < cantVertices; i++) {
                if (matrizAdyacencia[v][i] && !visitados[i]) {
                    visitados[i] = true;
                    padre[i] = v;
                    if (i == t) { encontrado = true; break; }
                    cola.encolar(i);
                }
            }
        }
        if (!encontrado) return new String[0];
        Pila<String> pila = new Pila<>();
        int actual = t;
        while (actual != -1) {
            pila.apilar(vertices[actual]);
            actual = padre[actual];
        }
        String[] camino = new String[pila.tamanio()];
        int i = 0;
        while (!pila.estaVacia()) camino[i++] = pila.desapilar();
        return camino;
    }

    public int cantidadVertices() {
        return cantVertices;
    }
}
