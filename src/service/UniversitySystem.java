package service;

import model.*;
import tda.*;

public class UniversitySystem {

    private Avl<Student>                   estudiantesAvl;
    private Diccionario<Integer, Student>   estudiantesDic;
    private Abb<Subject>                    materiasBst;
    private Diccionario<String, Subject>    materiasDic;    // lookup directo de materia por codigo
    private ArbolB                          registroBtree;
    private Cola<Student>                   colaComun;
    private ColaPrioridad<Student>          colaPrioritaria;
    private Pila<Operation>                 historial;
    private ArbolGenerico<String>           estructuraAcademica;
    private Grafo                           correlatividades;
    private Diccionario<String, String>     profesores;
    private ConsultasService                consultas;

    public UniversitySystem() {
        estudiantesAvl      = new Avl<>();
        estudiantesDic      = new Diccionario<>();
        materiasBst         = new Abb<>();
        materiasDic         = new Diccionario<>();
        registroBtree       = new ArbolB();
        colaComun           = new Cola<>();
        colaPrioritaria     = new ColaPrioridad<>();
        historial           = new Pila<>();
        estructuraAcademica = new ArbolGenerico<>();
        correlatividades    = new Grafo();
        profesores          = new Diccionario<>();
        consultas = new ConsultasService(
                estudiantesDic, materiasBst, materiasDic,
                correlatividades, colaPrioritaria, historial,
                estudiantesAvl, registroBtree);
    }

    // ── Estudiantes ──────────────────────────────────────────────────────────

    public void altaEstudiante(int legajo, String nombre) {
        if (estudiantesDic.contieneClave(legajo)) {
            System.out.println("Ya existe un estudiante con legajo " + legajo);
            return;
        }
        Student s = new Student(legajo, nombre);
        estudiantesAvl.insertar(s);
        estudiantesDic.poner(legajo, s);
        registroBtree.insertar(legajo);
        historial.apilar(new Operation("ALTA_ESTUDIANTE", "Legajo: " + legajo + " - " + nombre));
        System.out.println("Estudiante registrado: " + s);
    }

    public void bajaEstudiante(int legajo) {
        Student s = estudiantesDic.obtener(legajo);
        if (s == null) { System.out.println("Estudiante no encontrado."); return; }
        estudiantesAvl.eliminar(s);
        estudiantesDic.eliminar(legajo);
        historial.apilar(new Operation("BAJA_ESTUDIANTE", "Legajo: " + legajo + " - " + s.getNombre()));
        System.out.println("Estudiante eliminado: " + s);
    }

    public void buscarEstudiante(int legajo) {
        Student s = estudiantesDic.obtener(legajo);
        if (s != null) System.out.println("Encontrado: " + s);
        else System.out.println("Estudiante no encontrado.");
    }

    public void listarEstudiantes() {
        System.out.print("Estudiantes (por legajo): ");
        estudiantesAvl.enOrden();
    }

    public void mostrarAlturaAvl() {
        System.out.println("Nivel de organizacion del registro: " + estudiantesAvl.obtenerAltura());
    }

    public void mostrarFactorEquilibrio(int legajo) {
        Student s = estudiantesDic.obtener(legajo);
        if (s == null) { System.out.println("Estudiante no encontrado."); return; }
        System.out.println("Factor de equilibrio (legajo " + legajo + "): "
                + estudiantesAvl.obtenerFactorBalance(s));
    }

    public void registrarAprobacion(int legajo, String codigoMateria) {
        Student s = estudiantesDic.obtener(legajo);
        if (s == null) { System.out.println("Estudiante no encontrado."); return; }
        if (!materiasBst.buscar(new Subject(codigoMateria, ""))) {
            System.out.println("Materia no encontrada en el sistema.");
            return;
        }
        s.aprobarMateria(codigoMateria);
        historial.apilar(new Operation("APROBACION", "Legajo " + legajo + " aprobo " + codigoMateria));
        System.out.println("Materia " + codigoMateria + " registrada como aprobada para " + s);
    }

    // ── Materias ─────────────────────────────────────────────────────────────

    public void altaMateria(String codigo, String nombre) {
        Subject sub = new Subject(codigo, nombre);
        materiasBst.insertar(sub);
        materiasDic.poner(codigo, sub);
        correlatividades.agregarVertice(codigo);
        historial.apilar(new Operation("ALTA_MATERIA", codigo + " - " + nombre));
        System.out.println("Materia registrada: " + sub);
    }

    public void bajaMateria(String codigo) {
        Subject sub = new Subject(codigo, "");
        if (!materiasBst.buscar(sub)) { System.out.println("Materia no encontrada."); return; }
        materiasBst.eliminar(sub);
        materiasDic.eliminar(codigo);
        historial.apilar(new Operation("BAJA_MATERIA", "Codigo: " + codigo));
        System.out.println("Materia eliminada: " + codigo);
    }

    public void buscarMateria(String codigo) {
        boolean found = materiasBst.buscar(new Subject(codigo, ""));
        System.out.println(found ? "Materia encontrada: " + codigo : "Materia no encontrada.");
    }

    public void listarMaterias() {
        System.out.print("Materias (orden alfabetico): ");
        materiasBst.enOrden();
    }

    public void asociarProfesor(String codigoMateria, String nombreProfesor) {
        profesores.poner(codigoMateria, nombreProfesor);
        historial.apilar(new Operation("ASOCIAR_PROFESOR", nombreProfesor + " -> " + codigoMateria));
        System.out.println("Profesor " + nombreProfesor + " asociado a " + codigoMateria);
    }

    public void verProfesor(String codigoMateria) {
        String prof = profesores.obtener(codigoMateria);
        if (prof != null) System.out.println("Profesor de " + codigoMateria + ": " + prof);
        else System.out.println("Sin profesor asignado para " + codigoMateria);
    }

    // ── Estructura academica ─────────────────────────────────────────────────

    public void crearCarrera(String nombre) {
        estructuraAcademica.agregarRaiz(nombre);
        System.out.println("Carrera creada: " + nombre);
    }

    public void agregarAnio(String carrera, String anio) {
        estructuraAcademica.agregarHijo(carrera, anio);
        System.out.println("Anio agregado: " + anio + " a " + carrera);
    }

    public void agregarMateriaAAnio(String anio, String materia) {
        estructuraAcademica.agregarHijo(anio, materia);
        System.out.println("Materia " + materia + " agregada a " + anio);
    }

    public void recorridoProfundidad() {
        System.out.println("Recorrido en profundidad:");
        estructuraAcademica.recorridoProfundidad();
    }

    public void recorridoAmplitud() {
        System.out.print("Recorrido nivel por nivel: ");
        estructuraAcademica.recorridoAmplitud();
    }

    // ── Atencion de alumnos ───────────────────────────────────────────────────

    public void agregarColaComun(int legajo) {
        Student s = estudiantesDic.obtener(legajo);
        if (s == null) { System.out.println("Estudiante no encontrado."); return; }
        colaComun.encolar(s);
        System.out.println("Alumno en cola: " + s + " (posicion " + colaComun.tamanio() + ")");
    }

    public void atenderColaComun() {
        if (colaComun.estaVacia()) { System.out.println("Cola vacia."); return; }
        Student s = colaComun.desencolar();
        historial.apilar(new Operation("ATENCION_COMUN", "Legajo: " + s.getLegajo()));
        System.out.println("Atendido: " + s);
    }

    public void verProximoComun() {
        if (colaComun.estaVacia()) { System.out.println("Cola vacia."); return; }
        System.out.println("Proximo: " + colaComun.frente() + " | En cola: " + colaComun.tamanio());
    }

    public void agregarColaPrioritaria(int legajo, int prioridad) {
        Student s = estudiantesDic.obtener(legajo);
        if (s == null) { System.out.println("Estudiante no encontrado."); return; }
        colaPrioritaria.encolar(s, prioridad);
        System.out.println("Alumno en cola prioritaria: " + s + " (prioridad " + prioridad + ")");
    }

    public void atenderColaPrioritaria() {
        if (colaPrioritaria.estaVacio()) { System.out.println("Cola prioritaria vacia."); return; }
        Student s = colaPrioritaria.desencolar();
        historial.apilar(new Operation("ATENCION_PRIORITARIA", "Legajo: " + s.getLegajo()));
        System.out.println("Atendido (prioritario): " + s);
    }

    public void verProximoPrioritario() {
        if (colaPrioritaria.estaVacio()) { System.out.println("Cola prioritaria vacia."); return; }
        System.out.println("Proximo (prioritario): " + colaPrioritaria.verFrente()
                + " | En cola: " + colaPrioritaria.verTamanio());
    }

    public void estadoColas() {
        System.out.println("Cola comun: " + (colaComun.estaVacia() ? "vacia" : colaComun.tamanio() + " alumnos"));
        System.out.println("Cola prioritaria: " + (colaPrioritaria.estaVacio() ? "vacia" : colaPrioritaria.verTamanio() + " alumnos"));
    }

    // ── Correlatividades ─────────────────────────────────────────────────────

    public void agregarMateriaVertice(String codigo) {
        correlatividades.agregarVertice(codigo);
        System.out.println("Vertice agregado: " + codigo);
    }

    public void agregarCorrelatividad(String previa, String posterior) {
        correlatividades.agregarArista(previa, posterior);
        historial.apilar(new Operation("CORRELATIVIDAD", previa + " -> " + posterior));
        System.out.println("Correlatividad: " + previa + " -> " + posterior);
    }

    public void bfsCorrelatividades(String inicio) {
        correlatividades.recorridoAmplitud(inicio);
    }

    public void dfsCorrelatividades(String inicio) {
        correlatividades.recorridoProfundidad(inicio);
    }

    public void verificarHabilitacion(String origen, String destino) {
        boolean ok = correlatividades.existeCamino(origen, destino);
        System.out.println(ok
                ? origen + " habilita (directa o indirectamente) a " + destino
                : origen + " NO habilita a " + destino);
    }

    // ── Historial ────────────────────────────────────────────────────────────

    public void deshacerUltimaOperacion() {
        if (historial.estaVacia()) { System.out.println("No hay operaciones en el historial."); return; }
        Operation op = historial.desapilar();
        System.out.println("Operacion deshecha: " + op);
    }

    public void mostrarHistorial() {
        if (historial.estaVacia()) { System.out.println("Historial vacio."); return; }
        Pila<Operation> temp = new Pila<>();
        System.out.println("--- Historial (mas reciente primero) ---");
        while (!historial.estaVacia()) {
            Operation op = historial.desapilar();
            System.out.println(op);
            temp.apilar(op);
        }
        while (!temp.estaVacia()) historial.apilar(temp.desapilar());
    }

    // ── ArbolB ───────────────────────────────────────────────────────────────

    public void mostrarRegistrosBtree() {
        System.out.print("Legajos registrados (orden): ");
        registroBtree.enOrden();
    }

    public void buscarEnBtree(int legajo) {
        boolean found = registroBtree.buscar(legajo);
        System.out.println(found ? "Legajo " + legajo + " encontrado en el registro."
                                 : "Legajo no encontrado en el registro.");
    }

    // ── Consultas complejas (delegadas a ConsultasService) ───────────────────

    public void puedesCursarMateria(int legajo, String codigoMateria) {
        consultas.puedesCursarMateria(legajo, codigoMateria);
    }

    public void caminoCorrelatividades(String origen, String destino) {
        consultas.caminoCorrelatividades(origen, destino);
    }

    public void atenderYRegistrar() {
        consultas.atenderYRegistrar();
    }

    public void deshacerYMostrarEstado() {
        consultas.deshacerYMostrarEstado();
    }

    // ── Estado general ────────────────────────────────────────────────────────

    public void estadoGeneral() {
        System.out.println("=== ESTADO GENERAL DEL SISTEMA ===");
        System.out.println("Estudiantes registrados (nivel " + estudiantesAvl.obtenerAltura() + "):");
        estudiantesAvl.enOrden();
        System.out.println("Materias registradas:");
        materiasBst.enOrden();
        System.out.println("Legajos registrados:");
        registroBtree.enOrden();
        estadoColas();
        System.out.println("Vertices en grafo de correlativas: " + correlatividades.cantidadVertices());
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
        registrarAprobacion(1001, "ALG1");
        registrarAprobacion(1001, "MAT1");
        registrarAprobacion(1001, "PRG1");
        registrarAprobacion(1002, "MAT1");
        System.out.println("Datos de prueba cargados.");
    }
}
