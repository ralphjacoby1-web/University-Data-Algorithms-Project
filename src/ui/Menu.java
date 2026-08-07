package ui;

import service.UniversitySystem;

import java.util.Scanner;

public class Menu {

    private Scanner scanner;
    private UniversitySystem sistema;

    public Menu() {
        scanner = new Scanner(System.in);
        sistema = new UniversitySystem();
    }

    public void iniciar() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n========================================");
            System.out.println(" SISTEMA DE GESTION UNIVERSITARIA");
            System.out.println("========================================");
            System.out.println("1. Cargar datos de prueba");
            System.out.println("2. Gestionar estudiantes");
            System.out.println("3. Gestionar materias");
            System.out.println("4. Gestionar estructura academica");
            System.out.println("5. Atencion de alumnos");
            System.out.println("6. Gestionar correlatividades");
            System.out.println("7. Historial de operaciones");
            System.out.println("8. Consultas complejas");
            System.out.println("9. Estado general del sistema");
            System.out.println("0. Salir");
            System.out.println("========================================");
            System.out.print("Seleccione una opcion: ");
            opcion = leerInt();

            switch (opcion) {
                case 1: sistema.cargarDatosPrueba(); break;
                case 2: menuEstudiantes(); break;
                case 3: menuMaterias(); break;
                case 4: menuEstructura(); break;
                case 5: menuAtencion(); break;
                case 6: menuCorrelatividades(); break;
                case 7: menuHistorial(); break;
                case 8: menuConsultas(); break;
                case 9: sistema.estadoGeneral(); break;
                case 0: System.out.println("Saliendo..."); break;
                default: System.out.println("Opcion invalida.");
            }
        }
        scanner.close();
    }

    // ── Estudiantes ──────────────────────────────────────────────────────────

    private void menuEstudiantes() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- GESTION DE ESTUDIANTES ---");
            System.out.println("1. Alta de estudiante");
            System.out.println("2. Baja de estudiante");
            System.out.println("3. Buscar estudiante por legajo");
            System.out.println("4. Listar estudiantes ordenados por legajo");
            System.out.println("5. Ver nivel de organizacion del registro");
            System.out.println("6. Ver factor de equilibrio de un estudiante");
            System.out.println("7. Listar todos los legajos del sistema");
            System.out.println("8. Registrar materia aprobada por un estudiante");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            op = leerInt();
            switch (op) {
                case 1:
                    System.out.print("Legajo: ");
                    int legajo = leerInt();
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    sistema.altaEstudiante(legajo, nombre);
                    break;
                case 2:
                    System.out.print("Legajo a eliminar: ");
                    sistema.bajaEstudiante(leerInt());
                    break;
                case 3:
                    System.out.print("Legajo a buscar: ");
                    sistema.buscarEstudiante(leerInt());
                    break;
                case 4:
                    sistema.listarEstudiantes();
                    break;
                case 5:
                    sistema.mostrarAlturaAvl();
                    break;
                case 6:
                    System.out.print("Legajo del nodo: ");
                    sistema.mostrarFactorEquilibrio(leerInt());
                    break;
                case 7:
                    sistema.mostrarRegistrosBtree();
                    break;
                case 8:
                    System.out.print("Legajo: ");
                    int legApr = leerInt();
                    System.out.print("Codigo de materia aprobada: ");
                    String codApr = scanner.nextLine();
                    sistema.registrarAprobacion(legApr, codApr);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    // ── Materias ─────────────────────────────────────────────────────────────

    private void menuMaterias() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- GESTION DE MATERIAS ---");
            System.out.println("1. Alta de materia");
            System.out.println("2. Baja de materia");
            System.out.println("3. Buscar materia por codigo");
            System.out.println("4. Listar materias en orden alfabetico");
            System.out.println("5. Asociar profesor a materia");
            System.out.println("6. Ver profesor de una materia");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            op = leerInt();
            switch (op) {
                case 1:
                    System.out.print("Codigo: ");
                    String cod = scanner.nextLine();
                    System.out.print("Nombre: ");
                    String nom = scanner.nextLine();
                    sistema.altaMateria(cod, nom);
                    break;
                case 2:
                    System.out.print("Codigo a eliminar: ");
                    sistema.bajaMateria(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("Codigo a buscar: ");
                    sistema.buscarMateria(scanner.nextLine());
                    break;
                case 4:
                    sistema.listarMaterias();
                    break;
                case 5:
                    System.out.print("Codigo de materia: ");
                    String codMat = scanner.nextLine();
                    System.out.print("Nombre del profesor: ");
                    String prof = scanner.nextLine();
                    sistema.asociarProfesor(codMat, prof);
                    break;
                case 6:
                    System.out.print("Codigo de materia: ");
                    sistema.verProfesor(scanner.nextLine());
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    // ── Estructura academica ─────────────────────────────────────────────────

    private void menuEstructura() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- ESTRUCTURA ACADEMICA ---");
            System.out.println("1. Crear carrera");
            System.out.println("2. Agregar anio a carrera");
            System.out.println("3. Agregar materia a un anio");
            System.out.println("4. Ver estructura completa (en profundidad)");
            System.out.println("5. Ver estructura nivel por nivel");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            op = leerInt();
            switch (op) {
                case 1:
                    System.out.print("Nombre de carrera: ");
                    sistema.crearCarrera(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Nombre de carrera: ");
                    String carrera = scanner.nextLine();
                    System.out.print("Anio (ej: 1er Anio): ");
                    String anio = scanner.nextLine();
                    sistema.agregarAnio(carrera, anio);
                    break;
                case 3:
                    System.out.print("Anio (ej: 1er Anio): ");
                    String a = scanner.nextLine();
                    System.out.print("Codigo de materia: ");
                    String mat = scanner.nextLine();
                    sistema.agregarMateriaAAnio(a, mat);
                    break;
                case 4:
                    sistema.recorridoProfundidad();
                    break;
                case 5:
                    sistema.recorridoAmplitud();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    // ── Atencion de alumnos ───────────────────────────────────────────────────

    private void menuAtencion() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- ATENCION DE ALUMNOS ---");
            System.out.println("1. Agregar alumno a cola comun");
            System.out.println("2. Atender proximo alumno comun");
            System.out.println("3. Ver proximo alumno comun");
            System.out.println("4. Agregar alumno a cola prioritaria");
            System.out.println("5. Atender alumno prioritario");
            System.out.println("6. Ver proximo alumno prioritario");
            System.out.println("7. Estado de ambas colas");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            op = leerInt();
            switch (op) {
                case 1:
                    System.out.print("Legajo: ");
                    sistema.agregarColaComun(leerInt());
                    break;
                case 2:
                    sistema.atenderColaComun();
                    break;
                case 3:
                    sistema.verProximoComun();
                    break;
                case 4:
                    System.out.print("Legajo: ");
                    int leg = leerInt();
                    System.out.print("Prioridad (numero positivo): ");
                    int pri = leerInt();
                    sistema.agregarColaPrioritaria(leg, pri);
                    break;
                case 5:
                    sistema.atenderColaPrioritaria();
                    break;
                case 6:
                    sistema.verProximoPrioritario();
                    break;
                case 7:
                    sistema.estadoColas();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    // ── Correlatividades ─────────────────────────────────────────────────────

    private void menuCorrelatividades() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- CORRELATIVIDADES ---");
            System.out.println("1. Agregar materia a la red de correlatividades");
            System.out.println("2. Agregar relacion entre materias");
            System.out.println("3. Explorar correlativas nivel por nivel");
            System.out.println("4. Explorar correlativas en profundidad");
            System.out.println("5. Verificar si una materia habilita a otra");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            op = leerInt();
            switch (op) {
                case 1:
                    System.out.print("Codigo de materia: ");
                    sistema.agregarMateriaVertice(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Materia previa (origen): ");
                    String origen = scanner.nextLine();
                    System.out.print("Materia posterior (destino): ");
                    String destino = scanner.nextLine();
                    sistema.agregarCorrelatividad(origen, destino);
                    break;
                case 3:
                    System.out.print("Materia inicial: ");
                    sistema.bfsCorrelatividades(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Materia inicial: ");
                    sistema.dfsCorrelatividades(scanner.nextLine());
                    break;
                case 5:
                    System.out.print("Materia origen: ");
                    String org = scanner.nextLine();
                    System.out.print("Materia destino: ");
                    String dst = scanner.nextLine();
                    sistema.verificarHabilitacion(org, dst);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    // ── Historial ────────────────────────────────────────────────────────────

    private void menuHistorial() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- HISTORIAL DE OPERACIONES ---");
            System.out.println("1. Ver historial completo");
            System.out.println("2. Deshacer ultima operacion");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            op = leerInt();
            switch (op) {
                case 1: sistema.mostrarHistorial(); break;
                case 2: sistema.deshacerUltimaOperacion(); break;
                case 0: break;
                default: System.out.println("Opcion invalida.");
            }
        }
    }

    // ── Consultas complejas ───────────────────────────────────────────────────

    private void menuConsultas() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- CONSULTAS COMPLEJAS ---");
            System.out.println("1. Buscar estudiante en el sistema");
            System.out.println("2. Verificar si puedo cursar una materia");// [Dictionary + Bst + Graph + aprobadas]
            System.out.println("3. Buscar legajo en el registro");
            System.out.println("4. Camino de correlatividades entre dos materias");// [Graph + Queue + Stack + Bst + Dictionary]
            System.out.println("5. Atender siguiente alumno prioritario y registrar");//[PriorityQueue + Dictionary + Stack]
            System.out.println("6. Deshacer ultima operacion y ver estado de indices");//[Stack + Avl + Bst + Btree]
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            op = leerInt();
            switch (op) {
                case 1:
                    System.out.print("Legajo: ");
                    sistema.buscarEstudiante(leerInt());
                    break;
                case 2:
                    System.out.print("Legajo del estudiante: ");
                    int leg = leerInt();
                    System.out.print("Codigo de materia: ");
                    String cod = scanner.nextLine();
                    sistema.puedesCursarMateria(leg, cod);
                    break;
                case 3:
                    System.out.print("Legajo a buscar en el registro: ");
                    sistema.buscarEnBtree(leerInt());
                    break;
                case 4:
                    System.out.print("Materia origen: ");
                    String cOrigen = scanner.nextLine();
                    System.out.print("Materia destino: ");
                    String cDestino = scanner.nextLine();
                    sistema.caminoCorrelatividades(cOrigen, cDestino);
                    break;
                case 5:
                    sistema.atenderYRegistrar();
                    break;
                case 6:
                    sistema.deshacerYMostrarEstado();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private int leerInt() {
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }
}
