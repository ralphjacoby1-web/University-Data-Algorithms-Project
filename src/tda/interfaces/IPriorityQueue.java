package tda.interfaces;

public interface IPriorityQueue<T> {

    void encolar(T elemento, int prioridad);
    T desencolar();
    T verFrente();
    int verTamanio();
    boolean estaVacio();

}
