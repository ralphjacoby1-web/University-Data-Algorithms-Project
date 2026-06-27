package tda.interfaces;

public interface IPila<T> {
    void apilar(T elemento);
    T desapilar();
    T tope();
    boolean estaVacia();
    int tamanio();
}
