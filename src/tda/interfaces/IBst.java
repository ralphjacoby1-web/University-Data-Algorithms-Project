package tda.interfaces;

public interface IBst<T extends Comparable<T>> {
    void insertar(T elemento);
    void eliminar(T elemento);
    boolean buscar(T elemento);
    void enOrden();
    T max();
    T min();
}
