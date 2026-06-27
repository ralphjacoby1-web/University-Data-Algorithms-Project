package service;

import model.*;
import tda.*;

public class ConsultasService {

    private final Dictionary<Integer, Student> estudiantesDic;
    private final Bst<Subject>                 materiasBst;
    private final Dictionary<String, Subject>  materiasDic;
    private final Graph                        correlatividades;
    private final PriorityQueue<Student>       colaPrioritaria;
    private final Stack<Operation>             historial;
    private final Avl<Student>                 estudiantesAvl;
    private final Btree                        registroBtree;
    // Set de alumnos ya atendidos por esta consulta; evita duplicados entre llamadas
    private final Dictionary<Integer, Boolean> yaAtendidos;

    public ConsultasService(
            Dictionary<Integer, Student> estudiantesDic,
            Bst<Subject> materiasBst,
            Dictionary<String, Subject> materiasDic,
            Graph correlatividades,
            PriorityQueue<Student> colaPrioritaria,
            Stack<Operation> historial,
            Avl<Student> estudiantesAvl,
            Btree registroBtree) {
        this.estudiantesDic  = estudiantesDic;
        this.materiasBst     = materiasBst;
        this.materiasDic     = materiasDic;
        this.correlatividades = correlatividades;
        this.colaPrioritaria  = colaPrioritaria;
        this.historial        = historial;
        this.estudiantesAvl   = estudiantesAvl;
        this.registroBtree    = registroBtree;
        this.yaAtendidos      = new Dictionary<>();
    }

    // Dictionary + Bst + Graph (BFS inverso sobre correlativas) + Dictionary interno de Student
    public void puedesCursarMateria(int legajo, String codigoMateria) {
        Student s = estudiantesDic.get(legajo);
        if (s == null) { System.out.println("Estudiante no encontrado."); return; }
        if (!materiasBst.buscar(new Subject(codigoMateria, ""))) {
            System.out.println("Materia no encontrada en el sistema.");
            return;
        }
        System.out.println("=== Consulta: puede cursar? ===");
        System.out.println("[Dictionary] Estudiante  : " + s);
        System.out.println("[Bst]        Materia     : " + codigoMateria);
        String[] prereqs = correlatividades.prerequisitosTransitivos(codigoMateria);
        System.out.println("[Graph]      Correlativas requeridas (" + prereqs.length + "):");
        Stack<String> faltantes = new Stack<>();
        for (String prereq : prereqs) {
            boolean aprobada = s.tieneAprobada(prereq);
            System.out.println("  - " + prereq + ": " + (aprobada ? "APROBADA" : "PENDIENTE"));
            if (!aprobada) faltantes.push(prereq);
        }
        if (faltantes.isEmpty()) {
            System.out.println(">>> PUEDE CURSAR " + codigoMateria);
        } else {
            System.out.println(">>> NO PUEDE CURSAR " + codigoMateria);
            System.out.print("    Correlativas faltantes: ");
            while (!faltantes.isEmpty()) System.out.print(faltantes.pop() + "  ");
            System.out.println();
        }
    }

    // Graph (caminoBFS usa Queue + Stack internamente) + Bst (verificar existencia) + Dictionary (nombre completo)
    public void caminoCorrelatividades(String origen, String destino) {
        System.out.println("=== Consulta: camino de correlatividades ===");
        System.out.println("[Graph + Queue + Stack] Buscando camino (BFS) de "
                + origen + " a " + destino + "...");
        String[] camino = correlatividades.caminoBFS(origen, destino);
        if (camino.length == 0) {
            System.out.println("No existe camino de correlatividades entre " + origen + " y " + destino);
            return;
        }
        System.out.println("[Graph] Camino encontrado (" + camino.length + " materias):");
        for (int i = 0; i < camino.length; i++) {
            String codigo = camino[i];
            boolean enBst = materiasBst.buscar(new Subject(codigo, ""));
            Subject sub   = materiasDic.get(codigo);
            String nombre = (sub != null) ? sub.getNombre() : "(sin datos)";
            String flecha = (i < camino.length - 1) ? " --> " : "";
            System.out.println("  [" + (i + 1) + "] " + codigo
                    + " (" + nombre + ")"
                    + (enBst ? " [en Bst]" : " [no en Bst]")
                    + flecha);
        }
    }

    // PriorityQueue + Dictionary (datos del alumno) + Dictionary (yaAtendidos, antiduplic) + Stack (historial)
    public void atenderYRegistrar() {
        System.out.println("=== Consulta: atender alumno prioritario y registrar ===");
        if (colaPrioritaria.estaVacio()) {
            System.out.println("[PriorityQueue] Cola prioritaria vacia.");
            return;
        }
        Student extraido = colaPrioritaria.desencolar();
        System.out.println("[PriorityQueue] Alumno extraido: " + extraido);
        Student completo = estudiantesDic.get(extraido.getLegajo());
        if (completo == null) {
            System.out.println("[Dictionary] Advertencia: el alumno ya no esta en el sistema.");
            return;
        }
        System.out.println("[Dictionary]    Datos completos: " + completo);
        if (yaAtendidos.containsKey(completo.getLegajo())) {
            System.out.println("[Dictionary] Atencion: este alumno ya fue atendido anteriormente.");
        } else {
            yaAtendidos.put(completo.getLegajo(), true);
            System.out.println("[Dictionary] Alumno registrado como atendido.");
        }
        historial.push(new Operation("ATENCION_PRIORITARIA_REG", "Legajo: " + completo.getLegajo()));
        System.out.println(">>> Atencion completada: " + completo);
    }

    // Stack (historial) + Avl + Bst + Btree: desapila la ultima operacion y muestra el estado de los tres indices
    public void deshacerYMostrarEstado() {
        System.out.println("=== Consulta: deshacer ultima operacion y ver estado de indices ===");
        if (historial.isEmpty()) {
            System.out.println("[Stack] No hay operaciones en el historial.");
            return;
        }
        Operation op = historial.pop();
        System.out.println("[Stack] Operacion deshecha: " + op);
        String tipo = op.getTipo();
        System.out.println("Indices afectados y su estado actual:");
        if (tipo.contains("ESTUDIANTE") || tipo.equals("APROBACION")) {
            System.out.print("[Avl]   Estudiantes en orden: ");
            estudiantesAvl.enOrden();
            System.out.println("[Avl]   Nivel de organizacion: " + estudiantesAvl.obtenerAltura());
            System.out.print("[Btree] Legajos registrados  : ");
            registroBtree.enOrden();
        }
        if (tipo.contains("MATERIA") || tipo.equals("ASOCIAR_PROFESOR")) {
            System.out.print("[Bst]   Materias en orden    : ");
            materiasBst.enOrden();
        }
        if (tipo.contains("CORRELATIVIDAD")) {
            System.out.println("[Graph] Vertices en el grafo de correlativas: "
                    + correlatividades.getVertexCount());
        }
        System.out.println("--- Resumen post-operacion [Avl + Bst + Btree] ---");
        System.out.print("[Avl]   : ");
        estudiantesAvl.enOrden();
        System.out.print("[Bst]   : ");
        materiasBst.enOrden();
        System.out.print("[Btree] : ");
        registroBtree.enOrden();
    }
}
