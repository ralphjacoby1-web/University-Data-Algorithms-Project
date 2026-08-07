package tda.interfaces;

public interface IDiccionario<K, V> {
    void poner(K clave, V valor);
    V obtener(K clave);
    void eliminar(K clave);
    boolean contieneClave(K clave);
    boolean estaVacio();
    int tamanio();
}
