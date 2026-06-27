package service;

import model.*;
import tda.*;

public class ConsultasService {

    private final Diccionario<Integer, Student> estudiantesDic;
    private final Abb<Subject>                  materiasBst;
    private final Diccionario<String, Subject>  materiasDic;
    private final Grafo                         correlatividades;
    private final ColaPrioridad<Student>        colaPrioritaria;
    private final Pila<Operation>               historial;
    private final Avl<Student>                  estudiantesAvl;
    private final ArbolB                        registroBtree;
    // Set de alumnos ya atendidos; evita duplicados entre llamadas
    private final Diccionario<Integer, Boolean> yaAtendidos;

    public ConsultasService(
            Diccionario<Integer, Student> estudiantesDic,
            Abb<Subject> materiasBst,
            Diccionario<String, Subject> materiasDic,
            Grafo correlatividades,
            ColaPrioridad<Student> colaPrioritaria,
            Pila<Operation> historial,
            Avl<Student> estudiantesAvl,
            ArbolB registroBtree) {
        this.estudiantesDic  = estudiantesDic;
        this.materiasBst     = materiasBst;
        this.materiasDic     = materiasDic;
        this.correlatividades = correlatividades;
        this.colaPrioritaria  = colaPrioritaria;
        this.historial        = historial;
        this.estudiantesAvl   = estudiantesAvl;
        this.registroBtree    = registroBtree;
        this.yaAtendidos      = new Diccionario<>();
    }

    // Diccionario + Abb + Grafo (BFS inverso sobre correlativas) + Diccionario interno de Student
    public void puedesCursarMateria(int legajo, String codigoMateria) {
        Student s = estudiantesDic.obtener(legajo);
        if (s == null) { System.out.println("Estudiante no encontrado."); return; }
        if (!materiasBst.buscar(new Subject(codigoMateria, ""))) {
            System.out.println("Materia no encontrada en el sistema.");
            return;
        }
        System.out.println("=== Consulta: puede cursar? ===");
        System.out.println("[Diccionario] Estudiante  : " + s);
        System.out.println("[Abb]         Materia     : " + codigoMateria);
        String[] prereqs = correlatividades.prerequisitosTransitivos(codigoMateria);
        System.out.println("[Grafo]       Correlativas requeridas (" + prereqs.length + "):");
        Pila<String> faltantes = new Pila<>();
        for (String prereq : prereqs) {
            boolean aprobada = s.tieneAprobada(prereq);
            System.out.println("  - " + prereq + ": " + (aprobada ? "APROBADA" : "PENDIENTE"));
            if (!aprobada) faltantes.apilar(prereq);
        }
        if (faltantes.estaVacia()) {
            System.out.println(">>> PUEDE CURSAR " + codigoMateria);
        } else {
            System.out.println(">>> NO PUEDE CURSAR " + codigoMateria);
            System.out.print("    Correlativas faltantes: ");
            while (!faltantes.estaVacia()) System.out.print(faltantes.desapilar() + "  ");
            System.out.println();
        }
    }

    // Grafo (caminoAmplitud usa Cola + Pila internamente) + Abb (verificar existencia) + Diccionario (nombre completo)
    public void caminoCorrelatividades(String origen, String destino) {
        System.out.println("=== Consulta: camino de correlatividades ===");
        System.out.println("[Grafo + Cola + Pila] Buscando camino (BFS) de "
                + origen + " a " + destino + "...");
        String[] camino = correlatividades.caminoAmplitud(origen, destino);
        if (camino.length == 0) {
            System.out.println("No existe camino de correlatividades entre " + origen + " y " + destino);
            return;
        }
        System.out.println("[Grafo] Camino encontrado (" + camino.length + " materias):");
        for (int i = 0; i < camino.length; i++) {
            String codigo = camino[i];
            boolean enAbb = materiasBst.buscar(new Subject(codigo, ""));
            Subject sub   = materiasDic.obtener(codigo);
            String nombre = (sub != null) ? sub.getNombre() : "(sin datos)";
            String flecha = (i < camino.length - 1) ? " --> " : "";
            System.out.println("  [" + (i + 1) + "] " + codigo
                    + " (" + nombre + ")"
                    + (enAbb ? " [en Abb]" : " [no en Abb]")
                    + flecha);
        }
    }

    // ColaPrioridad + Diccionario (datos del alumno) + Diccionario (yaAtendidos, antiduplic) + Pila (historial)
    public void atenderYRegistrar() {
        System.out.println("=== Consulta: atender alumno prioritario y registrar ===");
        if (colaPrioritaria.estaVacio()) {
            System.out.println("[ColaPrioridad] Cola prioritaria vacia.");
            return;
        }
        Student extraido = colaPrioritaria.desencolar();
        System.out.println("[ColaPrioridad] Alumno extraido: " + extraido);
        Student completo = estudiantesDic.obtener(extraido.getLegajo());
        if (completo == null) {
            System.out.println("[Diccionario] Advertencia: el alumno ya no esta en el sistema.");
            return;
        }
        System.out.println("[Diccionario]   Datos completos: " + completo);
        if (yaAtendidos.contieneClave(completo.getLegajo())) {
            System.out.println("[Diccionario] Atencion: este alumno ya fue atendido anteriormente.");
        } else {
            yaAtendidos.poner(completo.getLegajo(), true);
            System.out.println("[Diccionario] Alumno registrado como atendido.");
        }
        historial.apilar(new Operation("ATENCION_PRIORITARIA_REG", "Legajo: " + completo.getLegajo()));
        System.out.println(">>> Atencion completada: " + completo);
    }

    // Pila (historial) + Avl + Abb + ArbolB: desapila la ultima operacion y muestra el estado de los tres indices
    public void deshacerYMostrarEstado() {
        System.out.println("=== Consulta: deshacer ultima operacion y ver estado de indices ===");
        if (historial.estaVacia()) {
            System.out.println("[Pila] No hay operaciones en el historial.");
            return;
        }
        Operation op = historial.desapilar();
        System.out.println("[Pila] Operacion deshecha: " + op);
        String tipo = op.getTipo();
        System.out.println("Indices afectados y su estado actual:");
        if (tipo.contains("ESTUDIANTE") || tipo.equals("APROBACION")) {
            System.out.print("[Avl]    Estudiantes en orden: ");
            estudiantesAvl.enOrden();
            System.out.println("[Avl]    Nivel de organizacion: " + estudiantesAvl.obtenerAltura());
            System.out.print("[ArbolB] Legajos registrados  : ");
            registroBtree.enOrden();
        }
        if (tipo.contains("MATERIA") || tipo.equals("ASOCIAR_PROFESOR")) {
            System.out.print("[Abb]    Materias en orden    : ");
            materiasBst.enOrden();
        }
        if (tipo.contains("CORRELATIVIDAD")) {
            System.out.println("[Grafo]  Vertices en el grafo de correlativas: "
                    + correlatividades.cantidadVertices());
        }
        System.out.println("--- Resumen post-operacion [Avl + Abb + ArbolB] ---");
        System.out.print("[Avl]    : ");
        estudiantesAvl.enOrden();
        System.out.print("[Abb]    : ");
        materiasBst.enOrden();
        System.out.print("[ArbolB] : ");
        registroBtree.enOrden();
    }
}
