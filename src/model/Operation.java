package model;

public class Operation {
    private String tipo;
    private String descripcion;

    public Operation(String tipo, String descripcion) {
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public String getTipo() { return tipo; }
    public String getDescripcion() { return descripcion; }

    @Override
    public String toString() { return "[" + tipo + "] " + descripcion; }
}
