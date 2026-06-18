package model;

public class Career {
    private String nombre;

    public Career(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }

    @Override
    public String toString() { return nombre; }
}
