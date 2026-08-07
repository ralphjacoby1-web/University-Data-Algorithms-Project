package tda.interfaces;

public interface IAbb<T extends Comparable<T>> {
    void insertar(T elemento);
    void eliminar(T elemento);
    boolean buscar(T elemento);
    void enOrden();
    T maximo();
    T minimo();
}
