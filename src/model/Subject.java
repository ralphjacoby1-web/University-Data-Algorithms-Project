package model;

public class Subject implements Comparable<Subject> {
    private String codigo;
    private String nombre;

    public Subject(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }

    @Override
    public int compareTo(Subject other) {
        return this.codigo.compareTo(other.codigo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Subject)) return false;
        return this.codigo.equals(((Subject) obj).codigo);
    }

    @Override
    public int hashCode() { return codigo.hashCode(); }

    @Override
    public String toString() { return codigo + " - " + nombre; }
}
