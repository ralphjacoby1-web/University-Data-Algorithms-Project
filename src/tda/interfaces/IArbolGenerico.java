package tda.interfaces;

public interface IArbolGenerico<T> {
    void agregarRaiz(T elemento);
    void agregarHijo(T padre, T hijo);
    boolean estaVacio();
    void recorridoProfundidad();
    void recorridoAmplitud();
}
