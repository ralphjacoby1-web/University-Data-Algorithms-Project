package tda.interfaces;

public interface IGraph {
    void addVertex(String vertex);
    void addEdge(String from, String to);
    void bfs(String start);
    void dfs(String start);
    boolean hasPath(String from, String to);
}
