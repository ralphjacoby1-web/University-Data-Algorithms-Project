package tda.interfaces;

public interface IGrafo {
    void agregarVertice(String vertice);
    void agregarArista(String origen, String destino);
    void recorridoAmplitud(String inicio);
    void recorridoProfundidad(String inicio);
    boolean existeCamino(String origen, String destino);
}
