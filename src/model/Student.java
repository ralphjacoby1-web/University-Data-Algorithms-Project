package model;

public class Student implements Comparable<Student> {
    private int legajo;
    private String nombre;

    public Student(int legajo, String nombre) {
        this.legajo = legajo;
        this.nombre = nombre;
    }

    public int getLegajo() { return legajo; }
    public String getNombre() { return nombre; }

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
