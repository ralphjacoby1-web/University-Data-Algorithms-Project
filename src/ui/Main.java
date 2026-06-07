package ui;

import tda.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        //Menu menu = new Menu();
        //menu.iniciar();

        PriorityQueue<String> colaPrioridad = new PriorityQueue<>();

        System.out.println(colaPrioridad.estaVacio());
        colaPrioridad.encolar("carlos", 5);
        System.out.println(colaPrioridad.estaVacio());
        colaPrioridad.encolar("mazza", 2);
        colaPrioridad.encolar("nahuel", 10);
        System.out.println(colaPrioridad.verTamanio());
        System.out.println(colaPrioridad.verFrente());
        colaPrioridad.encolar("coso", -2);
        System.out.println(colaPrioridad.verTamanio());
        System.out.println(colaPrioridad.verFrente());
        System.out.println(colaPrioridad.desencolar());
        System.out.println(colaPrioridad.verFrente());
        colaPrioridad.encolar("asf", 1);
        System.out.println(colaPrioridad.desencolar());
        System.out.println(colaPrioridad.desencolar());
        System.out.println(colaPrioridad.desencolar());
        System.out.println(colaPrioridad.desencolar());

    }
}