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

    public int getVertexCount() { return vertexCount; }
}
