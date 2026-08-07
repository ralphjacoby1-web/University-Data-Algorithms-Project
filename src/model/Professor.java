package model;

public class Professor {
    private String nombre;
    private String codigoMateria;

    public Professor(String nombre, String codigoMateria) {
        this.nombre = nombre;
        this.codigoMateria = codigoMateria;
    }

    public String getNombre() { return nombre; }
    public String getCodigoMateria() { return codigoMateria; }

    @Override
    public String toString() { return nombre + " (" + codigoMateria + ")"; }
}
