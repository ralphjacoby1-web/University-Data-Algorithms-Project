package tda.interfaces;

public interface IGenericTree<T> {
    void addRoot(T element);
    void addChild(T parent, T child);
    void dfs();
    void bfs();
}
