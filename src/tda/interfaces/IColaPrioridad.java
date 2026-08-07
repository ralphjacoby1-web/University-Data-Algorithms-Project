package tda.interfaces;

public interface IColaPrioridad<T> {
    void encolar(T elemento, int prioridad);
    T desencolar();
    T verFrente();
    int verTamanio();
    boolean estaVacio();
}
