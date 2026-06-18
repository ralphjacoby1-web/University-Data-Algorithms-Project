package service;

import model.*;
import tda.*;

public class UniversitySystem {

    private Avl<Student>             estudiantesAvl;
    private Dictionary<Integer, Student> estudiantesDic;
    private Bst<Subject>             materiasBst;
    private Btree                    registroBtree;
    private Queue<Student>           colaComun;
    private PriorityQueue<Student>   colaPrioritaria;
    private Stack<Operation>         historial;
    private GenericTree<String>      estructuraAcademica;
    private Graph                    correlatividades;
    private Dictionary<String, String> profesores;

    public UniversitySystem() {
        estudiantesAvl      = new Avl<>();
        estudiantesDic      = new Dictionary<>();
        materiasBst         = new Bst<>();
        registroBtree       = new Btree();
        colaComun           = new Queue<>();
        colaPrioritaria     = new PriorityQueue<>();
        historial           = new Stack<>();
        estructuraAcademica = new GenericTree<>();
        correlatividades    = new Graph();
        profesores          = new Dictionary<>();
    }

    // ── Estudiantes ──────────────────────────────────────────────────────────

    public void altaEstudiante(int legajo, String nombre) {
        if (estudiantesDic.containsKey(legajo)) {
            System.out.println("Ya existe un estudiante con legajo " + legajo);
            return;
        }
        Student s = new Student(legajo, nombre);
        estudiantesAvl.insert(s);
        estudiantesDic.put(legajo, s);
        registroBtree.insert(legajo);
        historial.push(new Operation("ALTA_ESTUDIANTE", "Legajo: " + legajo + " - " + nombre));
        System.out.println("Estudiante registrado: " + s);
    }

    public void bajaEstudiante(int legajo) {
        Student s = estudiantesDic.get(legajo);
        if (s == null) { System.out.println("Estudiante no encontrado."); return; }
        estudiantesAvl.delete(s);
        estudiantesDic.remove(legajo);
        historial.push(new Operation("BAJA_ESTUDIANTE", "Legajo: " + legajo + " - " + s.getNombre()));
        System.out.println("Estudiante eliminado: " + s);
    }

    public void buscarEstudiante(int legajo) {
        Student s = estudiantesDic.get(legajo);
        if (s != null) System.out.println("Encontrado: " + s);
        else System.out.println("Estudiante no encontrado.");
    }

    public void listarEstudiantes() {
        System.out.print("Estudiantes (por legajo): ");
        estudiantesAvl.inOrder();
    }

    public void mostrarAlturaAvl() {
        System.out.println("Altura del AVL: " + estudiantesAvl.getHeight());
    }

    public void mostrarFactorEquilibrio(int legajo) {
        Student s = estudiantesDic.get(legajo);
        if (s == null) { System.out.println("Estudiante no encontrado."); return; }
        System.out.println("Factor de equilibrio (legajo " + legajo + "): "
                + estudiantesAvl.getBalanceFactor(s));
    }

    // ── Materias ─────────────────────────────────────────────────────────────

    public void altaMateria(String codigo, String nombre) {
        Subject sub = new Subject(codigo, nombre);
        materiasBst.insert(sub);
        correlatividades.addVertex(codigo);
        historial.push(new Operation("ALTA_MATERIA", codigo + " - " + nombre));
        System.out.println("Materia registrada: " + sub);
    }

    public void bajaMateria(String codigo) {
        Subject sub = new Subject(codigo, "");
        if (!materiasBst.search(sub)) { System.out.println("Materia no encontrada."); return; }
        materiasBst.delete(sub);
        historial.push(new Operation("BAJA_MATERIA", "Codigo: " + codigo));
        System.out.println("Materia eliminada: " + codigo);
    }

    public void buscarMateria(String codigo) {
        boolean found = materiasBst.search(new Subject(codigo, ""));
        System.out.println(found ? "Materia encontrada: " + codigo : "Materia no encontrada.");
    }

    public void listarMaterias() {
        System.out.print("Materias (orden alfabetico): ");
        materiasBst.inOrder();
    }

    public void asociarProfesor(String codigoMateria, String nombreProfesor) {
        profesores.put(codigoMateria, nombreProfesor);
        historial.push(new Operation("ASOCIAR_PROFESOR", nombreProfesor + " -> " + codigoMateria));
        System.out.println("Profesor " + nombreProfesor + " asociado a " + codigoMateria);
    }

    public void verProfesor(String codigoMateria) {
        String prof = profesores.get(codigoMateria);
        if (prof != null) System.out.println("Profesor de " + codigoMateria + ": " + prof);
        else System.out.println("Sin profesor asignado para " + codigoMateria);
    }

    // ── Estructura academica (GenericTree) ───────────────────────────────────

    public void crearCarrera(String nombre) {
        estructuraAcademica.addRoot(nombre);
        System.out.println("Carrera creada: " + nombre);
    }

    public void agregarAnio(String carrera, String anio) {
        estructuraAcademica.addChild(carrera, anio);
        System.out.println("Anio agregado: " + anio + " a " + carrera);
    }

    public void agregarMateriaAAnio(String anio, String materia) {
        estructuraAcademica.addChild(anio, materia);
        System.out.println("Materia " + materia + " agregada a " + anio);
    }

    public void recorridoProfundidad() {
        System.out.println("Recorrido en profundidad (DFS):");
        estructuraAcademica.dfs();
    }

    public void recorridoAmplitud() {
        System.out.print("Recorrido en amplitud (BFS): ");
        estructuraAcademica.bfs();
    }

    // ── Atencion de alumnos ───────────────────────────────────────────────────

    public void agregarColaComun(int legajo) {
        Student s = estudiantesDic.get(legajo);
        if (s == null) { System.out.println("Estudiante no encontrado."); return; }
        colaComun.enqueue(s);
        System.out.println("Alumno en cola: " + s + " (posicion " + colaComun.size() + ")");
    }

    public void atenderColaComun() {
        if (colaComun.isEmpty()) { System.out.println("Cola vacia."); return; }
        Student s = colaComun.dequeue();
        historial.push(new Operation("ATENCION_COMUN", "Legajo: " + s.getLegajo()));
        System.out.println("Atendido: " + s);
    }

    public void verProximoComun() {
        if (colaComun.isEmpty()) { System.out.println("Cola vacia."); return; }
        System.out.println("Proximo: " + colaComun.front() + " | En cola: " + colaComun.size());
    }

    public void agregarColaPrioritaria(int legajo, int prioridad) {
        Student s = estudiantesDic.get(legajo);
        if (s == null) { System.out.println("Estudiante no encontrado."); return; }
        colaPrioritaria.encolar(s, prioridad);
        System.out.println("Alumno en cola prioritaria: " + s + " (prioridad " + prioridad + ")");
    }

    public void atenderColaPrioritaria() {
        if (colaPrioritaria.estaVacio()) { System.out.println("Cola prioritaria vacia."); return; }
        Student s = colaPrioritaria.desencolar();
        historial.push(new Operation("ATENCION_PRIORITARIA", "Legajo: " + s.getLegajo()));
        System.out.println("Atendido (prioritario): " + s);
    }

    public void verProximoPrioritario() {
        if (colaPrioritaria.estaVacio()) { System.out.println("Cola prioritaria vacia."); return; }
        System.out.println("Proximo (prioritario): " + colaPrioritaria.verFrente()
                + " | En cola: " + colaPrioritaria.verTamanio());
    }

    public void estadoColas() {
        System.out.println("Cola comun: " + (colaComun.isEmpty() ? "vacia" : colaComun.size() + " alumnos"));
        System.out.println("Cola prioritaria: " + (colaPrioritaria.estaVacio() ? "vacia" : colaPrioritaria.verTamanio() + " alumnos"));
    }

    // ── Correlatividades (Graph) ──────────────────────────────────────────────

    public void agregarMateriaVertice(String codigo) {
        correlatividades.addVertex(codigo);
        System.out.println("Vertice agregado: " + codigo);
    }

    public void agregarCorrelatividad(String previa, String posterior) {
        correlatividades.addEdge(previa, posterior);
        historial.push(new Operation("CORRELATIVIDAD", previa + " -> " + posterior));
        System.out.println("Correlatividad: " + previa + " -> " + posterior);
    }

    public void bfsCorrelatividades(String inicio) {
        correlatividades.bfs(inicio);
    }

    public void dfsCorrelatividades(String inicio) {
        correlatividades.dfs(inicio);
    }

    public void verificarHabilitacion(String origen, String destino) {
        boolean ok = correlatividades.hasPath(origen, destino);
        System.out.println(ok
                ? origen + " habilita (directa o indirectamente) a " + destino
                : origen + " NO habilita a " + destino);
    }

    // ── Historial / Stack ────────────────────────────────────────────────────

    public void deshacerUltimaOperacion() {
        if (historial.isEmpty()) { System.out.println("No hay operaciones en el historial."); return; }
        Operation op = historial.pop();
        System.out.println("Operacion deshecha: " + op);
    }

    public void mostrarHistorial() {
        if (historial.isEmpty()) { System.out.println("Historial vacio."); return; }
        Stack<Operation> temp = new Stack<>();
        System.out.println("--- Historial (mas reciente primero) ---");
        while (!historial.isEmpty()) {
            Operation op = historial.pop();
            System.out.println(op);
            temp.push(op);
        }
        while (!temp.isEmpty()) historial.push(temp.pop());
    }

    // ── BTree ────────────────────────────────────────────────────────────────

    public void mostrarRegistrosBtree() {
        System.out.print("Legajos en Btree (orden): ");
        registroBtree.inOrder();
    }

    public void buscarEnBtree(int legajo) {
        boolean found = registroBtree.search(legajo);
        System.out.println(found ? "Legajo " + legajo + " encontrado en Btree." : "Legajo no encontrado en Btree.");
    }

    // ── Consultas complejas ───────────────────────────────────────────────────

    public void puedesCursarMateria(int legajo, String codigoMateria) {
        Student s = estudiantesDic.get(legajo);
        if (s == null) { System.out.println("Estudiante no encontrado."); return; }
        boolean materiaExiste = materiasBst.search(new Subject(codigoMateria, ""));
        if (!materiaExiste) { System.out.println("Materia no encontrada en el sistema."); return; }
        System.out.println("Estudiante: " + s);
        System.out.println("Materia solicitada: " + codigoMateria);
        System.out.println("(Para verificar correlativas ingrese la materia previa requerida)");
    }

    public void estadoGeneral() {
        System.out.println("=== ESTADO GENERAL DEL SISTEMA ===");
        System.out.println("Estudiantes registrados (AVL, altura " + estudiantesAvl.getHeight() + "):");
        estudiantesAvl.inOrder();
        System.out.println("Materias registradas (BST):");
        materiasBst.inOrder();
        System.out.println("Legajos en Btree:");
        registroBtree.inOrder();
        estadoColas();
        System.out.println("Vertices en grafo de correlativas: " + correlatividades.getVertexCount());
    }

    // ── Datos de prueba ───────────────────────────────────────────────────────

    public void cargarDatosPrueba() {
        System.out.println("Cargando datos de prueba...");

        altaEstudiante(1001, "Ana Garcia");
        altaEstudiante(1002, "Bruno Lopez");
        altaEstudiante(1003, "Carla Perez");
        altaEstudiante(1004, "Diego Morales");
        altaEstudiante(1005, "Elena Torres");

        altaMateria("ALG1", "Algebra 1");
        altaMateria("MAT1", "Matematica 1");
        altaMateria("MAT2", "Matematica 2");
        altaMateria("PRG1", "Programacion 1");
        altaMateria("PRG2", "Programacion 2");
        altaMateria("EDA2", "Estructuras de Datos 2");

        asociarProfesor("MAT1", "Dr. Ramirez");
        asociarProfesor("PRG1", "Lic. Gomez");
        asociarProfesor("EDA2", "Dr. Fernandez");

        agregarCorrelatividad("MAT1", "MAT2");
        agregarCorrelatividad("ALG1", "MAT2");
        agregarCorrelatividad("PRG1", "PRG2");
        agregarCorrelatividad("PRG2", "EDA2");
        agregarCorrelatividad("MAT2", "EDA2");

        crearCarrera("Ingenieria en Sistemas");
        agregarAnio("Ingenieria en Sistemas", "1er Anio");
        agregarAnio("Ingenieria en Sistemas", "2do Anio");
        agregarAnio("Ingenieria en Sistemas", "3er Anio");
        agregarMateriaAAnio("1er Anio", "ALG1");
        agregarMateriaAAnio("1er Anio", "MAT1");
        agregarMateriaAAnio("1er Anio", "PRG1");
        agregarMateriaAAnio("2do Anio", "MAT2");
        agregarMateriaAAnio("2do Anio", "PRG2");
        agregarMateriaAAnio("3er Anio", "EDA2");

        System.out.println("Datos de prueba cargados.");
    }
}
