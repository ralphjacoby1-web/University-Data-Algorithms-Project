package model;

import tda.Dictionary;

public class Student implements Comparable<Student> {
    private int legajo;
    private String nombre;
    // Dictionary<codigoMateria, true> usado como Set de aprobadas
    private Dictionary<String, Boolean> materiasAprobadas;

    public Student(int legajo, String nombre) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.materiasAprobadas = new Dictionary<>();
    }

    public int getLegajo() { return legajo; }
    public String getNombre() { return nombre; }

    public void aprobarMateria(String codigoMateria) {
        materiasAprobadas.put(codigoMateria, true);
    }

    public boolean tieneAprobada(String codigoMateria) {
        return materiasAprobadas.containsKey(codigoMateria);
    }

    @Override
    public int compareTo(Student other) {
        return Integer.compare(this.legajo, other.legajo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Student)) return false;
        return this.legajo == ((Student) obj).legajo;
    }

    @Override
    public int hashCode() { return Integer.hashCode(legajo); }

    @Override
    public String toString() { return legajo + " - " + nombre; }
}
