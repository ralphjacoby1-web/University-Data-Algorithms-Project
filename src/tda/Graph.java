package tda;

import tda.interfaces.IGraph;

public class Graph implements IGraph {

    private static final int MAX_VERTICES = 50;
    private String[] vertices;
    private boolean[][] adjMatrix;
    private int vertexCount;

    public Graph() {
        vertices = new String[MAX_VERTICES];
        adjMatrix = new boolean[MAX_VERTICES][MAX_VERTICES];
        vertexCount = 0;
    }

    private int indexOf(String vertex) {
        for (int i = 0; i < vertexCount; i++)
            if (vertices[i].equals(vertex)) return i;
        return -1;
    }

    @Override
    public void addVertex(String vertex) {
        if (indexOf(vertex) != -1 || vertexCount >= MAX_VERTICES) return;
        vertices[vertexCount++] = vertex;
    }

    @Override
    public void addEdge(String from, String to) {
        int f = indexOf(from), t = indexOf(to);
        if (f == -1 || t == -1) return;
        adjMatrix[f][t] = true;
    }

    @Override
    public void bfs(String start) {
        int s = indexOf(start);
        if (s == -1) { System.out.println("Vertice no encontrado: " + start); return; }
        boolean[] visited = new boolean[vertexCount];
        Queue<Integer> queue = new Queue<>();
        visited[s] = true;
        queue.enqueue(s);
        System.out.print("BFS desde " + start + ": ");
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            System.out.print(vertices[v] + "  ");
            for (int i = 0; i < vertexCount; i++) {
                if (adjMatrix[v][i] && !visited[i]) {
                    visited[i] = true;
                    queue.enqueue(i);
                }
            }
        }
        System.out.println();
    }

    @Override
    public void dfs(String start) {
        int s = indexOf(start);
        if (s == -1) { System.out.println("Vertice no encontrado: " + start); return; }
        boolean[] visited = new boolean[vertexCount];
        System.out.print("DFS desde " + start + ": ");
        dfsRec(s, visited);
        System.out.println();
    }

    private void dfsRec(int v, boolean[] visited) {
        visited[v] = true;
        System.out.print(vertices[v] + "  ");
        for (int i = 0; i < vertexCount; i++) {
            if (adjMatrix[v][i] && !visited[i])
                dfsRec(i, visited);
        }
    }

    @Override
    public boolean hasPath(String from, String to) {
        int f = indexOf(from), t = indexOf(to);
        if (f == -1 || t == -1) return false;
        boolean[] visited = new boolean[vertexCount];
        return hasPathRec(f, t, visited);
    }

    private boolean hasPathRec(int from, int to, boolean[] visited) {
        if (from == to) return true;
        visited[from] = true;
        for (int i = 0; i < vertexCount; i++) {
            if (adjMatrix[from][i] && !visited[i])
                if (hasPathRec(i, to, visited)) return true;
        }
        return false;
    }

    // Devuelve todos los vertices que son prerequisito (directo o transitivo) de 'destino'.
    // Usa BFS inverso: recorre las aristas al reves desde 'destino'.
    public String[] prerequisitosTransitivos(String destino) {
        int d = indexOf(destino);
        if (d == -1) return new String[0];
        boolean[] alcanzable = new boolean[vertexCount];
        Queue<Integer> cola = new Queue<>();
        alcanzable[d] = true;
        cola.enqueue(d);
        while (!cola.isEmpty()) {
            int v = cola.dequeue();
            for (int i = 0; i < vertexCount; i++) {
                if (adjMatrix[i][v] && !alcanzable[i]) { // arista i->v: i es prerequisito de v
                    alcanzable[i] = true;
                    cola.enqueue(i);
                }
            }
        }
        int count = 0;
        for (int i = 0; i < vertexCount; i++)
            if (alcanzable[i] && i != d) count++;
        String[] resultado = new String[count];
        int idx = 0;
        for (int i = 0; i < vertexCount; i++)
            if (alcanzable[i] && i != d) resultado[idx++] = vertices[i];
        return resultado;
    }

    // Devuelve el camino mas corto (BFS) de 'origen' a 'destino'.
    // Usa Queue para BFS y Stack para reconstruir el camino.
    // Retorna arreglo vacio si no existe camino.
    public String[] caminoBFS(String origen, String destino) {
        int s = indexOf(origen), t = indexOf(destino);
        if (s == -1 || t == -1) return new String[0];
        if (s == t) return new String[]{vertices[s]};
        boolean[] visited = new boolean[vertexCount];
        int[] padre = new int[vertexCount];
        for (int i = 0; i < vertexCount; i++) padre[i] = -1;
        Queue<Integer> cola = new Queue<>();
        visited[s] = true;
        cola.enqueue(s);
        boolean encontrado = false;
        while (!cola.isEmpty() && !encontrado) {
            int v = cola.dequeue();
            for (int i = 0; i < vertexCount; i++) {
                if (adjMatrix[v][i] && !visited[i]) {
                    visited[i] = true;
                    padre[i] = v;
                    if (i == t) { encontrado = true; break; }
                    cola.enqueue(i);
                }
            }
        }
        if (!encontrado) return new String[0];
        // Reconstruir camino con Stack
        Stack<String> pila = new Stack<>();
        int actual = t;
        while (actual != -1) {
            pila.push(vertices[actual]);
            actual = padre[actual];
        }
        String[] camino = new String[pila.size()];
        int i = 0;
        while (!pila.isEmpty()) camino[i++] = pila.pop();
        return camino;
    }

    public int getVertexCount() { return vertexCount; }
}
